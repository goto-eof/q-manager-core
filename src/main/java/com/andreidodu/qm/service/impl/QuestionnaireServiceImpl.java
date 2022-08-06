package com.andreidodu.qm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.andreidodu.qm.db.QuestionnaireDB;
import com.andreidodu.qm.dto.Questionnaire;
import com.andreidodu.qm.dto.Translation;
import com.andreidodu.qm.dto.input.QuestionnaireInsert;
import com.andreidodu.qm.dto.input.TranslationInsert;
import com.andreidodu.qm.mapper.QuestionnaireMapper;
import com.andreidodu.qm.repository.QuestionnaireDao;
import com.andreidodu.qm.service.QuestionnaireService;
import com.andreidodu.qm.service.TranslationService;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class QuestionnaireServiceImpl implements QuestionnaireService {

	private static final String HELP_QUESTION_CODE = "HELP";
	private static final String TITLE_QUESTION_CODE = "TITLE";

	@Autowired
	QuestionnaireDao questionnaireDao;

	@Autowired
	private TranslationService translationService;

	@Autowired
	private QuestionnaireMapper questionnaireMapper;

	@Override
	public Questionnaire create(QuestionnaireInsert questionnaireIn) {
		QuestionnaireDB db = this.questionnaireMapper.toDB(questionnaireIn);

		if (questionnaireIn.title() != null) {
			if (questionnaireIn.language() == null) {
				return null;
			}
			TranslationInsert translation = new TranslationInsert(questionnaireIn.questionnaireCode(),
					TITLE_QUESTION_CODE, questionnaireIn.language(), questionnaireIn.title());
			Translation title = this.translationService.create(translation);
			db.setTitleCode(title.subCode());
		}
		if (questionnaireIn.help() != null) {
			if (questionnaireIn.language() == null) {
				return null;
			}
			TranslationInsert translation = new TranslationInsert(questionnaireIn.questionnaireCode(),
					HELP_QUESTION_CODE, questionnaireIn.language(), questionnaireIn.help());
			Translation title = this.translationService.create(translation);
			db.setHelpCode(title.subCode());
		}

		db = this.questionnaireDao.save(db);
		return this.questionnaireMapper.toDTO(db);
	}

	@Override
	public QuestionnaireInsert update(QuestionnaireInsert questionnaireToUpdate) {
		QuestionnaireDB db = this.questionnaireDao.findByQuestionnaireCode(questionnaireToUpdate.questionnaireCode());
		if (db != null) {
			if (questionnaireToUpdate.title() != null) {
				if (questionnaireToUpdate.language() == null) {
					return null;
				}
				TranslationInsert translation = new TranslationInsert(db.getQuestionnaireCode(), TITLE_QUESTION_CODE,
						questionnaireToUpdate.language(), questionnaireToUpdate.title());
				Translation result = this.translationService.update(translation);
				if (result == null) {
					result = this.translationService.create(translation);
				}
				db.setHelpCode(result.subCode());
			}
			if (questionnaireToUpdate.help() != null) {
				if (questionnaireToUpdate.language() == null) {
					return null;
				}
				TranslationInsert translation = new TranslationInsert(db.getQuestionnaireCode(), HELP_QUESTION_CODE,
						questionnaireToUpdate.language(), questionnaireToUpdate.help());
				Translation result = this.translationService.update(translation);
				if (result == null) {
					result = this.translationService.create(translation);
				}
				db.setHelpCode(result.subCode());
			}
			this.questionnaireDao.save(db);
			return questionnaireToUpdate;
		}
		return null;
	}

}
