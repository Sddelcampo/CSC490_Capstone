package com.politicalpioneer.User;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.politicalpioneer.Party.Party;
import com.politicalpioneer.Party.PartyAlignment.PartyAlignment;
import com.politicalpioneer.Party.PartyAlignment.PartyAlignmentRepository;
import com.politicalpioneer.User.UserAlignment.UserAlignment;
import com.politicalpioneer.User.UserAlignment.UserAlignmentRepository;

@Service
public class QuestionnaireService {
    private static final List<String> ANSWER_LABELS = List.of(
            "Strongly Disagree", "Disagree", "Neutral", "Agree", "Strongly Agree");

    private static final List<Section> SECTIONS = List.of(
            section(1, "Free market capitalism", Axis.FREE_MARKET, new int[] { 3, 4 },
                    "The government should make no effort to pass regulations aimed at controlling the decisions made by private companies.",
                    "Individuals should be free to create their own businesses.",
                    "Workers unions should exist for most industries.",
                    "The government is responsible for regulating monopolistic practices of corporations.",
                    "Healthy competition should determine the price of goods, not government regulations."),
            section(6, "Government involvement", Axis.GOVERNMENT_INVOLVEMENT, new int[] { 2, 4 },
                    "The government should make no effort to regulate the personal beliefs and lifestyles of its citizens.",
                    "It is the role of the government to provide benefits for its citizens such as basic healthcare and education.",
                    "No laws should be made regarding the personal bodies of its individual citizens. Citizens should be able to do what they want with their bodies.",
                    "The government should ensure that children receive a basic quality of life. It should make sure that children are not being abused and are receiving a basic level of education.",
                    "The government should have a minimal role in the personal lives of its citizens."),
            section(11, "Social freedom", Axis.SOCIAL_FREEDOM, new int[] { 3, 5 },
                    "People should be free to practice different religions where they live, regardless of the country that they live in.",
                    "Adults should be free to marry any adult that they wish to, regardless of gender or race.",
                    "There should be restrictions on what people can change about their bodies.",
                    "People should be free to say what they want about the government and other people, so long as it is not actively putting people in harm's way.",
                    "Protesting should not be allowed if it impacts the lives of people not involved in the protest, and should be met with legal action."),
            section(16, "Foreign involvement", Axis.FOREIGN_INVOLVEMENT, new int[] { 4 },
                    "The government should try its best to remain impartial in any foreign conflict that it is not directly involved in.",
                    "The government should not get involved in foreign affairs, including medical problems.",
                    "It is not the role of the government to provide assistance to foreign countries or organizations.",
                    "The government should get involved in foreign wars if they are a threat to the power of the country.",
                    "We should try to keep the production of goods within the country. Goods produced locally should be promoted over imported foreign goods."),
            section(21, "Gun control", Axis.GUN_CONTROL, new int[] { 4 },
                    "To purchase a firearm, people should undergo basic background checks.",
                    "There should be additional requirements to purchase a firearm, such as mental health checks or mandatory wait periods.",
                    "People who purchase firearms should have to register said firearms with the government.",
                    "There should be no restrictions on what types of firearms individuals can own.",
                    "Minors (people under 18) should not be able to purchase or own a firearm."),
            section(26, "Environment and conservation", Axis.ENVIRONMENTAL, new int[] { 5 },
                    "We need to invest more into clean energy such as solar, wind, and hydropower.",
                    "There should be increased efforts to develop nuclear energy as a significant part of the energy grid.",
                    "We need to reduce our dependence on fossil fuels.",
                    "Companies should be held responsible for their environmental impacts and should do their best to minimize their carbon footprint.",
                    "There is no need to require companies to make efforts to prevent climate change."),
            section(31, "Technological advancement", Axis.TECHNOLOGICAL_ADVANCEMENT, new int[] { 2, 3 },
                    "We should prevent large data centers from being built in local communities.",
                    "Surveillance technologies like Flock cameras and other tracking devices should be welcomed in public spaces.",
                    "There should not be any efforts made to regulate how technology advances or is used.",
                    "The government should not be able to use any predictive models in law enforcement, such as technology that predicts a crime before it happens.",
                    "Technology should be used to benefit society and people, not to restrict the lives of the average person."));

    private final QuestionnaireAnswerRepository answerRepository;
    private final UserAlignmentRepository alignmentRepository;
    private final PartyAlignmentRepository partyAlignmentRepository;

    public QuestionnaireService(
            QuestionnaireAnswerRepository answerRepository,
            UserAlignmentRepository alignmentRepository,
            PartyAlignmentRepository partyAlignmentRepository) {
        this.answerRepository = answerRepository;
        this.alignmentRepository = alignmentRepository;
        this.partyAlignmentRepository = partyAlignmentRepository;
    }

    public int getTotalPages() {
        return SECTIONS.size();
    }

    public List<String> getSectionTitles() {
        return SECTIONS.stream().map(Section::title).toList();
    }

    public boolean isValidQuestion(int questionId) {
        return findQuestion(questionId).isPresent();
    }

    public Map<Integer, Integer> getAnswers(long userId) {
        return answerRepository.findAllByUser_Id(userId).stream()
                .collect(Collectors.toMap(QuestionnaireAnswer::getQuestionId, QuestionnaireAnswer::getValue));
    }

    public Map<String, Object> getSectionView(
            int pageNumber,
            Map<Integer, Integer> answers,
            UserAlignment alignment) {
        Section section = SECTIONS.get(pageNumber - 1);
        int score = alignment == null ? 0 : getScore(alignment, section.axis());
        return getSectionView(pageNumber, answers, score);
    }

    public Map<String, Object> getSectionView(
            int pageNumber,
            Map<Integer, Integer> answers,
            PartyAlignment alignment) {
        Section section = SECTIONS.get(pageNumber - 1);
        int score = alignment == null ? 0 : getScore(alignment, section.axis());
        return getSectionView(pageNumber, answers, score);
    }

    private Map<String, Object> getSectionView(
            int pageNumber,
            Map<Integer, Integer> answers,
            int score) {
        Section section = SECTIONS.get(pageNumber - 1);
        List<Map<String, Object>> questions = new ArrayList<>();

        for (Question question : section.questions()) {
            Map<String, Object> questionView = new LinkedHashMap<>();
            questionView.put("id", question.id());
            questionView.put("text", question.text());
            Integer answer = answers.get(question.id());
            if (answer != null) {
                questionView.put("answer", answer);
            }
            questions.add(questionView);
        }

        Map<String, Object> sectionView = new LinkedHashMap<>();
        sectionView.put("title", section.title());
        sectionView.put("questions", questions);
        sectionView.put("score", score);
        return sectionView;
    }

    public Map<String, Object> getResults(long userId) {
        Map<Integer, Integer> answers = getAnswers(userId);
        List<Map<String, Object>> sectionViews = new ArrayList<>();

        for (Section section : SECTIONS) {
            List<Map<String, Object>> questionViews = new ArrayList<>();
            double scoreTotal = 0;
            int answered = 0;

            for (Question question : section.questions()) {
                Map<String, Object> questionView = new LinkedHashMap<>();
                questionView.put("text", question.text());
                Integer answer = answers.get(question.id());
                int questionScore = 0;
                if (answer != null) {
                    questionView.put("answer", answer);
                    questionView.put("answerLabel", ANSWER_LABELS.get(answer - 1));
                    questionScore = effectiveValue(question, answer);
                    answered++;
                }
                scoreTotal += questionScore;
                questionViews.add(questionView);
            }

            double score = scoreTotal / section.questions().size();
            Map<String, Object> sectionView = new LinkedHashMap<>();
            sectionView.put("title", section.title());
            sectionView.put("answered", answered);
            sectionView.put("total", section.questions().size());
            sectionView.put("score", score);
            sectionView.put("percent", Math.round(score / 5 * 100));
            sectionView.put("questions", questionViews);
            sectionViews.add(sectionView);
        }

        int totalQuestions = SECTIONS.stream().mapToInt(section -> section.questions().size()).sum();
        return Map.of(
                "totalAnswered", answers.size(),
                "totalQuestions", totalQuestions,
                "sections", sectionViews);
    }

    @Transactional
    public int saveAnswer(User user, int questionId, int value) {
        if (value < 1 || value > 5) {
            throw new IllegalArgumentException("Answer value must be between 1 and 5");
        }
        Question question = findQuestion(questionId)
                .orElseThrow(() -> new IllegalArgumentException("Unknown questionnaire question"));

        UserAlignment alignment = alignmentRepository.findByIdForUpdate(user.getUserId())
                .orElse(user.getUserAlignment());
        if (alignment == null) {
            alignment = new UserAlignment();
            user.setUserAlignment(alignment);
        }

        Optional<QuestionnaireAnswer> existingAnswer = answerRepository
                .findByUser_IdAndQuestionId(user.getUserId(), questionId);
        int previousScore = existingAnswer
                .map(answer -> effectiveValue(question, answer.getValue()))
                .orElse(0);
        int newScore = effectiveValue(question, value);
        addScore(alignment, question.axis(), newScore - previousScore);

        QuestionnaireAnswer answer = existingAnswer.orElseGet(
                () -> new QuestionnaireAnswer(user, questionId, value));
        answer.setValue(value);

        alignmentRepository.save(alignment);
        answerRepository.save(answer);
        return getScore(alignment, question.axis());
    }

    @Transactional
    public int saveAnswerForParty(User user, Party party, int questionId, int value) {
        if (value < 1 || value > 5) {
            throw new IllegalArgumentException("Answer value must be between 1 and 5");
        }
        Question question = findQuestion(questionId)
                .orElseThrow(() -> new IllegalArgumentException("Unknown questionnaire question"));

        PartyAlignment alignment = partyAlignmentRepository.findByIdForUpdate(party.getPartyId())
                .orElse(party.getPartyAlignment());
        if (alignment == null) {
            alignment = new PartyAlignment();
            party.setPartyAlignment(alignment);
        }

        Map<Integer, Integer> answers = new LinkedHashMap<>(getAnswers(user.getUserId()));
        answers.put(questionId, value);
        for (Section section : SECTIONS) {
            int sectionScore = 0;
            for (Question sectionQuestion : section.questions()) {
                Integer sectionAnswer = answers.get(sectionQuestion.id());
                if (sectionAnswer != null) {
                    sectionScore += effectiveValue(sectionQuestion, sectionAnswer);
                }
            }
            setScore(alignment, section.axis(), sectionScore);
        }

        QuestionnaireAnswer answer = answerRepository
                .findByUser_IdAndQuestionId(user.getUserId(), questionId)
                .orElseGet(() -> new QuestionnaireAnswer(user, questionId, value));
        answer.setValue(value);

        partyAlignmentRepository.save(alignment);
        answerRepository.save(answer);
        return getScore(alignment, question.axis());
    }

    private static Optional<Question> findQuestion(int questionId) {
        return SECTIONS.stream()
                .flatMap(section -> section.questions().stream())
                .filter(question -> question.id() == questionId)
                .findFirst();
    }

    private static int effectiveValue(Question question, int value) {
        return question.inverse() ? 6 - value : value;
    }

    private static void addScore(UserAlignment alignment, Axis axis, int delta) {
        switch (axis) {
            case FREE_MARKET -> alignment.addFreemarketAlignment(delta);
            case GOVERNMENT_INVOLVEMENT -> alignment.addGovernmentInvolvementAlignment(delta);
            case SOCIAL_FREEDOM -> alignment.addSocialFreedomAlignment(delta);
            case FOREIGN_INVOLVEMENT -> alignment.addForeignInvolvementAlignment(delta);
            case GUN_CONTROL -> alignment.addGunControlAlignment(delta);
            case ENVIRONMENTAL -> alignment.addEnvironmentalAlignment(delta);
            case TECHNOLOGICAL_ADVANCEMENT -> alignment.addTechnologicalAdvancementAlignment(delta);
        }
    }

    private static void setScore(PartyAlignment alignment, Axis axis, int score) {
        switch (axis) {
            case FREE_MARKET -> alignment.setFreemarketAlignment(score);
            case GOVERNMENT_INVOLVEMENT -> alignment.setGovernmentInvolvementAlignment(score);
            case SOCIAL_FREEDOM -> alignment.setSocialFreedomAlignment(score);
            case FOREIGN_INVOLVEMENT -> alignment.setForeignInvolvementAlignment(score);
            case GUN_CONTROL -> alignment.setGunControlAlignment(score);
            case ENVIRONMENTAL -> alignment.setEnvironmentalAlignment(score);
            case TECHNOLOGICAL_ADVANCEMENT -> alignment.setTechnologicalAdvancementAlignment(score);
        }
    }

    private static int getScore(UserAlignment alignment, Axis axis) {
        return switch (axis) {
            case FREE_MARKET -> alignment.getFreemarketAlignment();
            case GOVERNMENT_INVOLVEMENT -> alignment.getGovernmentInvolvementAlignment();
            case SOCIAL_FREEDOM -> alignment.getSocialFreedomAlignment();
            case FOREIGN_INVOLVEMENT -> alignment.getForeignInvolvementAlignment();
            case GUN_CONTROL -> alignment.getGunControlAlignment();
            case ENVIRONMENTAL -> alignment.getEnvironmentalAlignment();
            case TECHNOLOGICAL_ADVANCEMENT -> alignment.getTechnologicalAdvancementAlignment();
        };
    }

    private static int getScore(PartyAlignment alignment, Axis axis) {
        return switch (axis) {
            case FREE_MARKET -> alignment.getFreemarketAlignment();
            case GOVERNMENT_INVOLVEMENT -> alignment.getGovernmentInvolvementAlignment();
            case SOCIAL_FREEDOM -> alignment.getSocialFreedomAlignment();
            case FOREIGN_INVOLVEMENT -> alignment.getForeignInvolvementAlignment();
            case GUN_CONTROL -> alignment.getGunControlAlignment();
            case ENVIRONMENTAL -> alignment.getEnvironmentalAlignment();
            case TECHNOLOGICAL_ADVANCEMENT -> alignment.getTechnologicalAdvancementAlignment();
        };
    }

    private static Section section(
            int firstQuestionId,
            String title,
            Axis axis,
            int[] inverseQuestionNumbers,
            String... questionTexts) {
        List<Question> questions = new ArrayList<>();
        for (int index = 0; index < questionTexts.length; index++) {
            int questionNumber = index + 1;
            boolean inverse = false;
            for (int inverseQuestionNumber : inverseQuestionNumbers) {
                if (questionNumber == inverseQuestionNumber) {
                    inverse = true;
                    break;
                }
            }
            questions.add(new Question(firstQuestionId + index, questionTexts[index], inverse, axis));
        }
        return new Section(title, axis, List.copyOf(questions));
    }

    private enum Axis {
        FREE_MARKET,
        GOVERNMENT_INVOLVEMENT,
        SOCIAL_FREEDOM,
        FOREIGN_INVOLVEMENT,
        GUN_CONTROL,
        ENVIRONMENTAL,
        TECHNOLOGICAL_ADVANCEMENT
    }

    private record Question(int id, String text, boolean inverse, Axis axis) {
    }

    private record Section(String title, Axis axis, List<Question> questions) {
    }
}
