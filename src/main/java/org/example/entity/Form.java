package org.example.entity; /***********************************************************************
 * Module:  Form.java
 * Author:  nguyenvanlap
 * Purpose: Defines the Class Form
 ***********************************************************************/

import java.util.*;

/** @pdOid 85a6a4a7-d4df-458f-a7dd-e71dbf1e04d2 */
public class Form {
   /** @pdOid 0a4e9753-c983-405a-b4a7-8b79e6516f38 */
   private String id;
   /** @pdOid faf23d0a-26a3-4576-b1aa-cffe135c3673 */
   private String name;
   /** @pdOid e4f3043d-ab26-4e9d-a59f-7e64e2ad0865 */
   private String introduction;
   
   /** @pdRoleInfo migr=no name=Question assc=association3 coll=java.util.Collection impl=java.util.HashSet mult=0..* */
   public java.util.Collection<Question> question;
   /** @pdRoleInfo migr=no name=FormAnswer assc=association4 coll=java.util.Collection impl=java.util.HashSet mult=0..* */
   public java.util.Collection<FormAnswer> formAnswer;
   
   
   /** @pdGenerated default getter */
   public java.util.Collection<Question> getQuestion() {
      if (question == null)
         question = new java.util.HashSet<Question>();
      return question;
   }
   
   /** @pdGenerated default iterator getter */
   public java.util.Iterator getIteratorQuestion() {
      if (question == null)
         question = new java.util.HashSet<Question>();
      return question.iterator();
   }
   
   /** @pdGenerated default setter
     * @param newQuestion */
   public void setQuestion(java.util.Collection<Question> newQuestion) {
      removeAllQuestion();
      for (java.util.Iterator iter = newQuestion.iterator(); iter.hasNext();)
         addQuestion((Question)iter.next());
   }
   
   /** @pdGenerated default add
     * @param newQuestion */
   public void addQuestion(Question newQuestion) {
      if (newQuestion == null)
         return;
      if (this.question == null)
         this.question = new java.util.HashSet<Question>();
      if (!this.question.contains(newQuestion))
         this.question.add(newQuestion);
   }
   
   /** @pdGenerated default remove
     * @param oldQuestion */
   public void removeQuestion(Question oldQuestion) {
      if (oldQuestion == null)
         return;
      if (this.question != null)
         if (this.question.contains(oldQuestion))
            this.question.remove(oldQuestion);
   }
   
   /** @pdGenerated default removeAll */
   public void removeAllQuestion() {
      if (question != null)
         question.clear();
   }
   /** @pdGenerated default getter */
   public java.util.Collection<FormAnswer> getFormAnswer() {
      if (formAnswer == null)
         formAnswer = new java.util.HashSet<FormAnswer>();
      return formAnswer;
   }
   
   /** @pdGenerated default iterator getter */
   public java.util.Iterator getIteratorFormAnswer() {
      if (formAnswer == null)
         formAnswer = new java.util.HashSet<FormAnswer>();
      return formAnswer.iterator();
   }
   
   /** @pdGenerated default setter
     * @param newFormAnswer */
   public void setFormAnswer(java.util.Collection<FormAnswer> newFormAnswer) {
      removeAllFormAnswer();
      for (java.util.Iterator iter = newFormAnswer.iterator(); iter.hasNext();)
         addFormAnswer((FormAnswer)iter.next());
   }
   
   /** @pdGenerated default add
     * @param newFormAnswer */
   public void addFormAnswer(FormAnswer newFormAnswer) {
      if (newFormAnswer == null)
         return;
      if (this.formAnswer == null)
         this.formAnswer = new java.util.HashSet<FormAnswer>();
      if (!this.formAnswer.contains(newFormAnswer))
         this.formAnswer.add(newFormAnswer);
   }
   
   /** @pdGenerated default remove
     * @param oldFormAnswer */
   public void removeFormAnswer(FormAnswer oldFormAnswer) {
      if (oldFormAnswer == null)
         return;
      if (this.formAnswer != null)
         if (this.formAnswer.contains(oldFormAnswer))
            this.formAnswer.remove(oldFormAnswer);
   }
   
   /** @pdGenerated default removeAll */
   public void removeAllFormAnswer() {
      if (formAnswer != null)
         formAnswer.clear();
   }

}