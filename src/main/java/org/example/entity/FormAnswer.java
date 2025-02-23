package org.example.entity; /***********************************************************************
 * Module:  FormAnswer.java
 * Author:  nguyenvanlap
 * Purpose: Defines the Class FormAnswer
 ***********************************************************************/

import java.util.*;

/** @pdOid 49eb33e5-b639-46f5-8e41-ec00a4721412 */
public class FormAnswer {
   /** @pdOid c7799298-49ce-444a-8efa-45394477eddb */
   private String id;
   /** @pdOid bcbcea12-c8ff-4e60-a1b9-c4d6179849dd */
   private String userId;
   /** @pdOid e95cc8a4-4962-4632-8cfa-d1a961e39dc7 */
   private String formId;
   
   /** @pdRoleInfo migr=no name=Answer assc=association5 coll=java.util.Collection impl=java.util.HashSet mult=0..* */
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