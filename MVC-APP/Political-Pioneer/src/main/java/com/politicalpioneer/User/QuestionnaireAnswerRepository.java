package com.politicalpioneer.User;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionnaireAnswerRepository extends JpaRepository<QuestionnaireAnswer, Long> {
    List<QuestionnaireAnswer> findAllByUser_Id(Long userId);

    Optional<QuestionnaireAnswer> findByUser_IdAndQuestionId(Long userId, int questionId);
}
