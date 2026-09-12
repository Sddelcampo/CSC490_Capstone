package com.politicalpioneer.ForumPost;

import java.util.List;

import org.springframework.stereotype.Service;

import com.politicalpioneer.BadRequestException;
import com.politicalpioneer.ResourceNotFoundException;

@Service
public class ForumPostService {

    private final ForumPostRepository forumRepo;

    public ForumPostService(ForumPostRepository forumRepo) {
        this.forumRepo = forumRepo;
    }

    public ForumPost saveForumPost(ForumPost post) {
        return forumRepo.save(post);
    }

    public List<ForumPost> getAllForumPosts() {
        if(forumRepo.findAll() == null) {
            throw new ResourceNotFoundException("Forum Posts not found");
        }
        return forumRepo.findAll();
    }

    public ForumPost getForumPostById(Long id) {
        if(!forumRepo.existsById(id)) {
            throw new BadRequestException("Forum Post not found");
        }

        return forumRepo.findById(id).orElse(null);
    }


    public List<ForumPost> getForumPostByUserId(Long id) {
        if(!forumRepo.existsById(id)) {
            throw new ResourceNotFoundException("Forum Post with ID " + id + " not found");
        }
        return forumRepo.findByUserId(id);
    }

    public ForumPost addForumPostBy(ForumPost newfp) {
        if(newfp == null) {
            throw new BadRequestException("Forum Post object null");
        }
        return forumRepo.save(newfp);
    }

    public ForumPost updateForumPost(Long id, ForumPost updatedfp) {
        ForumPost existFp = forumRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Forum Post with id " + id + " not found"));
        existFp.setTitle(updatedfp.getTitle());
        existFp.setContent(updatedfp.getContent());

        return forumRepo.save(existFp);

    }

    public void deleteForumPostById(Long id) {
        if(!forumRepo.existsById(id)) {
            throw new  ResourceNotFoundException("Forum Post with ID " + id + " not found");
        }
        forumRepo.deleteById(id);
    }



}
