package org.finra.interview.hua.api.file.controller;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.finra.interview.hua.api.aws.s3.boundary.AmazonS3Service;
import org.finra.interview.hua.api.file.entity.Metadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileServiceController {

    @Autowired
    private AmazonS3Service s3Service;

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public List<Metadata> getMetadatas() {
        return em.createNamedQuery("Metadata.getAll", Metadata.class).getResultList();
    }
    
    @Transactional
    public String upload(MultipartFile file) throws AmazonClientException, AmazonServiceException, IOException {
        String link = s3Service.upload(file.getOriginalFilename(), file);
        Metadata metadata = new Metadata();
        metadata.setName(file.getOriginalFilename());
        metadata.setSize(file.getSize());
        metadata.setLink(link);
        metadata.setUploadTime(Calendar.getInstance().getTime());
        em.persist(metadata);
        return link;
    }

    @Transactional
    public Metadata getMetadataById(Integer id) {
        return em.find(Metadata.class, id);
    }

    @Transactional
    public List<Integer> getIds(String name, Long size) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Metadata> query = builder.createQuery(Metadata.class);
        Root<Metadata> root = query.from(Metadata.class);
        query.select(root);
        Predicate predicate = null;
        if (!isEmpty(name) && !isEmpty(size)) {
            predicate = builder.and(builder.equal(root.get("name"), name), builder.equal(root.get("size"), size));
        } else if (!isEmpty(name) && isEmpty(size)) {
            predicate = builder.equal(root.get("name"), name);
        } else if (isEmpty(name) && !isEmpty(size)) {
            predicate = builder.equal(root.get("size"), size);
        }
        if (predicate != null) {
            query.select(root).where(predicate);
        } else {
            query.select(root);
        }

        List<Metadata> metadatas = em.createQuery(query).getResultList();
        List<Integer> ids = new ArrayList<>();
        metadatas.forEach((metadata) -> {
            ids.add(metadata.getId());
        });
        return ids;
    }

    private boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }

    private boolean isEmpty(Long size) {
        return size == null || size <= 0;
    }

    public List<Metadata> getLatestFile() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.HOUR, -1);
        return em.createNamedQuery("Metadata.getByUploadTime", Metadata.class)
                .setParameter("uploadTime", cal.getTime())
                .getResultList();
    }

}
