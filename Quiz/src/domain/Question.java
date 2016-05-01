package domain;

import java.util.ArrayList;
import java.util.List;

public class Question {
	private String description;
	private List<Answer> answers = new ArrayList<>();
	
	public Question(String description, Answer answer1, Answer answer2, Answer answer3 ,Answer answer4){
		this.setDescription(description);
		answers.add(answer1);
		answers.add(answer2);
		answers.add(answer3);
		answers.add(answer4);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		if(description == null)
			throw new NullPointerException("Text of the question cannot be null!");
		
		if(description.trim().equals(""))
			throw new RuntimeException("Text of the question cannot be an empty String!");
		
		this.description = description;
	}

	public List<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}
}
