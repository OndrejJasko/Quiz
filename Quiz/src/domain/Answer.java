package domain;

public class Answer {
	private String answerText;
	private boolean isCorrect;
	
	public Answer(String answerText, boolean isCorrect){
		this.setAnswerText(answerText);
		this.setCorrect(isCorrect);
	}

	public String getAnswerText() {
		return answerText;
	}

	public void setAnswerText(String answerText) {
		if(answerText == null)
			throw new NullPointerException("Text of the answer cannot be null!");
		
		if(answerText.trim().equals(""))
			throw new RuntimeException("Text of the answer cannot be an empty String!");
		
		this.answerText = answerText;
	}

	public boolean isCorrect() {
		return isCorrect;
	}

	public void setCorrect(boolean isCorrect) {
		this.isCorrect = isCorrect;
	}
}
