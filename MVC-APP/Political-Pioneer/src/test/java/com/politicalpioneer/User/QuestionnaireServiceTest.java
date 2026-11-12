package com.politicalpioneer.User;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.StringWriter;
import java.util.Optional;

import freemarker.template.Configuration;
import org.junit.jupiter.api.Test;

import com.politicalpioneer.Party.Party;
import com.politicalpioneer.Party.PartyAlignment.PartyAlignment;
import com.politicalpioneer.Party.PartyAlignment.PartyAlignmentRepository;
import com.politicalpioneer.User.UserAlignment.UserAlignment;
import com.politicalpioneer.User.UserAlignment.UserAlignmentRepository;

class QuestionnaireServiceTest {
    @Test
    void questionnaireTemplatesParse() throws Exception {
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_34);
        configuration.setDirectoryForTemplateLoading(
                new File("src/main/resources/templates"));

        configuration.getTemplate("questionnaire.ftlh");
        configuration.getTemplate("results.ftlh");
    }

    @Test
    void savesSelectedAnswerAsAlignmentScore() {
        QuestionnaireAnswerRepository answerRepository = mock(QuestionnaireAnswerRepository.class);
        UserAlignmentRepository alignmentRepository = mock(UserAlignmentRepository.class);
        User user = createUser();
        UserAlignment alignment = user.getUserAlignment();
        when(alignmentRepository.findByIdForUpdate(user.getUserId())).thenReturn(Optional.of(alignment));
        when(answerRepository.findByUser_IdAndQuestionId(user.getUserId(), 1)).thenReturn(Optional.empty());

        QuestionnaireService service = new QuestionnaireService(
                answerRepository, alignmentRepository, mock(PartyAlignmentRepository.class));
        int score = service.saveAnswer(user, 1, 4);

        assertEquals(4, score);
        assertEquals(4, alignment.getFreemarketAlignment());
        verify(answerRepository).save(org.mockito.ArgumentMatchers.any(QuestionnaireAnswer.class));
        verify(alignmentRepository).save(alignment);
    }

    @Test
    void savesOwnerAnswerToPartyAlignment() {
        QuestionnaireAnswerRepository answerRepository = mock(QuestionnaireAnswerRepository.class);
        UserAlignmentRepository alignmentRepository = mock(UserAlignmentRepository.class);
        PartyAlignmentRepository partyAlignmentRepository = mock(PartyAlignmentRepository.class);
        User user = createUser();
        Party party = new Party();
        party.setPartyId(12L);
        PartyAlignment alignment = new PartyAlignment();
        party.setPartyAlignment(alignment);
        when(partyAlignmentRepository.findByIdForUpdate(party.getPartyId()))
                .thenReturn(Optional.of(alignment));
        when(answerRepository.findAllByUser_Id(user.getUserId())).thenReturn(java.util.List.of());
        when(answerRepository.findByUser_IdAndQuestionId(user.getUserId(), 1)).thenReturn(Optional.empty());

        QuestionnaireService service = new QuestionnaireService(
                answerRepository, alignmentRepository, partyAlignmentRepository);
        int score = service.saveAnswerForParty(user, party, 1, 1);

        assertEquals(1, score);
        assertEquals(1, alignment.getFreemarketAlignment());
        verify(partyAlignmentRepository).save(alignment);
        verify(answerRepository).save(org.mockito.ArgumentMatchers.any(QuestionnaireAnswer.class));
    }

    @Test
    void ownerAnswerRecalculatesPartyAlignmentFromSavedAnswers() {
        QuestionnaireAnswerRepository answerRepository = mock(QuestionnaireAnswerRepository.class);
        UserAlignmentRepository alignmentRepository = mock(UserAlignmentRepository.class);
        PartyAlignmentRepository partyAlignmentRepository = mock(PartyAlignmentRepository.class);
        User user = createUser();
        Party party = new Party();
        party.setPartyId(12L);
        PartyAlignment alignment = new PartyAlignment();
        party.setPartyAlignment(alignment);
        when(partyAlignmentRepository.findByIdForUpdate(party.getPartyId()))
                .thenReturn(Optional.of(alignment));
        when(answerRepository.findAllByUser_Id(user.getUserId())).thenReturn(java.util.List.of(
                new QuestionnaireAnswer(user, 1, 2)));
        when(answerRepository.findByUser_IdAndQuestionId(user.getUserId(), 2)).thenReturn(Optional.empty());

        QuestionnaireService service = new QuestionnaireService(
                answerRepository, alignmentRepository, partyAlignmentRepository);
        int score = service.saveAnswerForParty(user, party, 2, 4);

        assertEquals(6, score);
        assertEquals(6, alignment.getFreemarketAlignment());
        verify(partyAlignmentRepository).save(alignment);
    }

    @Test
    void replacingAnswerReplacesItsContributionInsteadOfAddingAgain() {
        QuestionnaireAnswerRepository answerRepository = mock(QuestionnaireAnswerRepository.class);
        UserAlignmentRepository alignmentRepository = mock(UserAlignmentRepository.class);
        User user = createUser();
        UserAlignment alignment = user.getUserAlignment();
        alignment.setFreemarketAlignment(5);
        QuestionnaireAnswer existingAnswer = new QuestionnaireAnswer(user, 1, 5);
        when(alignmentRepository.findByIdForUpdate(user.getUserId())).thenReturn(Optional.of(alignment));
        when(answerRepository.findByUser_IdAndQuestionId(user.getUserId(), 1))
                .thenReturn(Optional.of(existingAnswer));

        QuestionnaireService service = new QuestionnaireService(
                answerRepository, alignmentRepository, mock(PartyAlignmentRepository.class));
        int score = service.saveAnswer(user, 1, 2);

        assertEquals(2, score);
        assertEquals(2, alignment.getFreemarketAlignment());
        assertEquals(2, existingAnswer.getValue());
        verify(answerRepository).save(existingAnswer);
    }

    @Test
    void reversesAnswersForQuestionsMarkedInverse() {
        QuestionnaireAnswerRepository answerRepository = mock(QuestionnaireAnswerRepository.class);
        UserAlignmentRepository alignmentRepository = mock(UserAlignmentRepository.class);
        User user = createUser();
        UserAlignment alignment = user.getUserAlignment();
        when(alignmentRepository.findByIdForUpdate(user.getUserId())).thenReturn(Optional.of(alignment));
        when(answerRepository.findByUser_IdAndQuestionId(user.getUserId(), 3)).thenReturn(Optional.empty());

        QuestionnaireService service = new QuestionnaireService(
                answerRepository, alignmentRepository, mock(PartyAlignmentRepository.class));
        int score = service.saveAnswer(user, 3, 1);

        assertEquals(5, score);
        assertEquals(5, alignment.getFreemarketAlignment());
    }

    @Test
    void resultsScoreUnansweredQuestionsAsZero() throws Exception {
        QuestionnaireAnswerRepository answerRepository = mock(QuestionnaireAnswerRepository.class);
        UserAlignmentRepository alignmentRepository = mock(UserAlignmentRepository.class);
        User user = createUser();
        when(answerRepository.findAllByUser_Id(user.getUserId())).thenReturn(java.util.List.of(
                new QuestionnaireAnswer(user, 1, 5),
                new QuestionnaireAnswer(user, 3, 1)));

        QuestionnaireService service = new QuestionnaireService(
                answerRepository, alignmentRepository, mock(PartyAlignmentRepository.class));
        var results = service.getResults(user.getUserId());

        assertEquals(2, results.get("totalAnswered"));
        assertEquals(35, results.get("totalQuestions"));
        @SuppressWarnings("unchecked")
        var sections = (java.util.List<java.util.Map<String, Object>>) results.get("sections");
        assertEquals(2.0, sections.get(0).get("score"));
        assertEquals(40L, sections.get(0).get("percent"));
        assertEquals(2, sections.get(0).get("answered"));

        Configuration configuration = new Configuration(Configuration.VERSION_2_3_34);
        configuration.setDirectoryForTemplateLoading(new File("src/main/resources/templates"));
        StringWriter rendered = new StringWriter();
        configuration.getTemplate("results.ftlh").process(results, rendered);
        assertTrue(rendered.toString().contains(
                "You answered <strong>2</strong> of <strong>35</strong> questions."));
    }

    @Test
    void allUnansweredQuestionsProduceAZeroScore() {
        QuestionnaireAnswerRepository answerRepository = mock(QuestionnaireAnswerRepository.class);
        UserAlignmentRepository alignmentRepository = mock(UserAlignmentRepository.class);
        User user = createUser();
        when(answerRepository.findAllByUser_Id(user.getUserId())).thenReturn(java.util.List.of());

        QuestionnaireService service = new QuestionnaireService(
                answerRepository, alignmentRepository, mock(PartyAlignmentRepository.class));
        var results = service.getResults(user.getUserId());

        @SuppressWarnings("unchecked")
        var sections = (java.util.List<java.util.Map<String, Object>>) results.get("sections");
        assertEquals(0.0, sections.get(0).get("score"));
        assertEquals(0L, sections.get(0).get("percent"));
    }

    @Test
    void onePointAnswerIsAveragedWithUnansweredQuestionsAsZero() throws Exception {
        QuestionnaireAnswerRepository answerRepository = mock(QuestionnaireAnswerRepository.class);
        UserAlignmentRepository alignmentRepository = mock(UserAlignmentRepository.class);
        User user = createUser();
        when(answerRepository.findAllByUser_Id(user.getUserId())).thenReturn(java.util.List.of(
                new QuestionnaireAnswer(user, 1, 1)));

        QuestionnaireService service = new QuestionnaireService(
                answerRepository, alignmentRepository, mock(PartyAlignmentRepository.class));
        var results = service.getResults(user.getUserId());

        @SuppressWarnings("unchecked")
        var sections = (java.util.List<java.util.Map<String, Object>>) results.get("sections");
        assertEquals(0.2, sections.get(0).get("score"));
        assertEquals(4L, sections.get(0).get("percent"));
        assertEquals(1, sections.get(0).get("answered"));

        Configuration configuration = new Configuration(Configuration.VERSION_2_3_34);
        configuration.setDirectoryForTemplateLoading(new File("src/main/resources/templates"));
        StringWriter rendered = new StringWriter();
        configuration.getTemplate("results.ftlh").process(results, rendered);
        assertTrue(rendered.toString().contains("Score: <strong>0.20</strong> / 5"));
    }

    private static User createUser() {
        return new User(1L, "Test", "User", "test-user", "password", "test@example.com", "active", "user");
    }
}
