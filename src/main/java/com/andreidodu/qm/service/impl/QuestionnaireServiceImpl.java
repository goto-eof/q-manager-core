package com.andreidodu.qm.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.andreidodu.qm.db.QuestionnaireDB;
import com.andreidodu.qm.dto.Questionnaire;
import com.andreidodu.qm.dto.input.QuestionnaireInsert;
import com.andreidodu.qm.mapper.QuestionnaireMapper;
import com.andreidodu.qm.repository.DaoFactory;
import com.andreidodu.qm.service.QuestionnaireService;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class QuestionnaireServiceImpl extends CommonServiceImpl<QuestionnaireDB, Long, Questionnaire, QuestionnaireInsert, DaoFactory, QuestionnaireMapper> implements QuestionnaireService {

	public QuestionnaireServiceImpl() {
		super(Questionnaire.class, QuestionnaireInsert.class);
	}

}
