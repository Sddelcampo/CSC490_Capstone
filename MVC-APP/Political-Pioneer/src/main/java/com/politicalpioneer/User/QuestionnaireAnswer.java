package com.politicalpioneer.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "questionnaire_answer", uniqueConstraints = @UniqueConstraint(
        name = "uq_questionnaire_answer_user_question",
        columnNames = { "user_id", "question_id" }))
public class QuestionnaireAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "answer_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "question_id", nullable = false)
    private int questionId;

    @Column(name = "answer_value", nullable = false)
    private int value;

    protected QuestionnaireAnswer() {
    }

    public QuestionnaireAnswer(User user, int questionId, int value) {
        this.user = user;
        this.questionId = questionId;
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
