package model;

import java.util.ArrayList;
import java.util.List;

import domain.Question;

/**
 * Class which contains list of questions and methods used for manipulation with that list.
 * 
 * @author Marko Stevankovic
 * @author Martin Veres
 * @author Filip Stojkovic
 */
public class CollectionOfQuestions {
	private static CollectionOfQuestions instance;
	private List<Question> questions;
	
	private CollectionOfQuestions(){
		questions = new ArrayList<>();
	}
	
	public static CollectionOfQuestions getInstance(){
		if(instance == null)
			instance = new CollectionOfQuestions();
		return instance;
	}
	
	public void addQuestion(Question question){
		if(!questions.contains(question))
			questions.add(question);
	}
	
	public List<Question> getAllQuestions(){
		return questions;
	}
}
