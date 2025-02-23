package org.example.entity; /***********************************************************************
 * Module:  Question.java
 * Author:  nguyenvanlap
 * Purpose: Defines the Class Question
 ***********************************************************************/

import java.util.*;

/** @pdOid 6d36e9ec-22ec-495f-949f-389cfc97eba1 */
public class Question {
   /** @pdOid 8aa46b2d-de9a-4914-976f-470fa3295e69 */
   private String id;
   /** @pdOid ad545b28-9375-4dc8-a30f-d49466f84521 */
   private int numericalOrder;
   /** @pdOid 6ebe0ead-d588-4fff-8b8b-98d18beb5938 */
   private String question;
   /** @pdOid 0eb2aa61-57b3-4ea9-b55f-f704c8441f69 */
   private String type;
   
   /** @pdRoleInfo migr=no name=Answer assc=association6 coll=java.util.Collection impl=java.util.HashSet mult=0..* */
   public java.util.Collection<Answer> answer;
   
   
   /** @pdGenerated default getter */
   public java.util.Collection<Answer> getAnswer() {
      if (answer == null)
         answer = new java.util.HashSet<Answer>();
      return answer;
   }
   
   /** @pdGenerated default iterator getter */
   public java.util.Iterator getIteratorAnswer() {
      if (answer == null)
         answer = new java.util.HashSet<Answer>();
      return answer.iterator();
   }
   
   /** @pdGenerated default setter
     * @param newAnswer */
   public void setAnswer(java.util.Collection<Answer> newAnswer) {
      removeAllAnswer();
      for (java.util.Iterator iter = newAnswer.iterator(); iter.hasNext();)
         addAnswer((Answer)iter.next());
   }
   
   /** @pdGenerated default add
     * @param newAnswer */
   public void addAnswer(Answer newAnswer) {
      if (newAnswer == null)
         return;
      if (this.answer == null)
         this.answer = new java.util.HashSet<Answer>();
      if (!this.answer.contains(newAnswer))
         this.answer.add(newAnswer);
   }
   
   /** @pdGenerated default remove
     * @param oldAnswer */
   public void removeAnswer(Answer oldAnswer) {
      if (oldAnswer == null)
         return;
      if (this.answer != null)
         if (this.answer.contains(oldAnswer))
            this.answer.remove(oldAnswer);
   }
   
   /** @pdGenerated default removeAll */
   public void removeAllAnswer() {
      if (answer != null)
         answer.clear();
   }

}