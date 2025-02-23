package org.example.entity; /***********************************************************************
 * Module:  Project.java
 * Author:  nguyenvanlap
 * Purpose: Defines the Class Project
 ***********************************************************************/

import java.util.*;

/** @pdOid 3e5e41fb-dc19-4e2f-8a2e-a95c9a4b536a */
public class Project {
   /** @pdOid 53349476-82aa-47f5-8bb4-798ac0c0f26d */
   private String id;
   /** @pdOid 4cf05540-ebd2-48d7-9238-675b8e11b0cb */
   private String name;
   /** @pdOid 5afa3b0c-2902-452e-a364-39a0375f0c06 */
   private String description;
   
   /** @pdRoleInfo migr=no name=Form assc=association2 coll=java.util.Collection impl=java.util.HashSet mult=0..* */
   public java.util.Collection<Form> form;
   /** @pdRoleInfo migr=no name=UserProject assc=association9 coll=java.util.Collection impl=java.util.HashSet mult=0..* */
   public java.util.Collection<UserProject> userProject;
   
   
   /** @pdGenerated default getter */
   public java.util.Collection<Form> getForm() {
      if (form == null)
         form = new java.util.HashSet<Form>();
      return form;
   }
   
   /** @pdGenerated default iterator getter */
   public java.util.Iterator getIteratorForm() {
      if (form == null)
         form = new java.util.HashSet<Form>();
      return form.iterator();
   }
   
   /** @pdGenerated default setter
     * @param newForm */
   public void setForm(java.util.Collection<Form> newForm) {
      removeAllForm();
      for (java.util.Iterator iter = newForm.iterator(); iter.hasNext();)
         addForm((Form)iter.next());
   }
   
   /** @pdGenerated default add
     * @param newForm */
   public void addForm(Form newForm) {
      if (newForm == null)
         return;
      if (this.form == null)
         this.form = new java.util.HashSet<Form>();
      if (!this.form.contains(newForm))
         this.form.add(newForm);
   }
   
   /** @pdGenerated default remove
     * @param oldForm */
   public void removeForm(Form oldForm) {
      if (oldForm == null)
         return;
      if (this.form != null)
         if (this.form.contains(oldForm))
            this.form.remove(oldForm);
   }
   
   /** @pdGenerated default removeAll */
   public void removeAllForm() {
      if (form != null)
         form.clear();
   }
   /** @pdGenerated default getter */
   public java.util.Collection<UserProject> getUserProject() {
      if (userProject == null)
         userProject = new java.util.HashSet<UserProject>();
      return userProject;
   }
   
   /** @pdGenerated default iterator getter */
   public java.util.Iterator getIteratorUserProject() {
      if (userProject == null)
         userProject = new java.util.HashSet<UserProject>();
      return userProject.iterator();
   }
   
   /** @pdGenerated default setter
     * @param newUserProject */
   public void setUserProject(java.util.Collection<UserProject> newUserProject) {
      removeAllUserProject();
      for (java.util.Iterator iter = newUserProject.iterator(); iter.hasNext();)
         addUserProject((UserProject)iter.next());
   }
   
   /** @pdGenerated default add
     * @param newUserProject */
   public void addUserProject(UserProject newUserProject) {
      if (newUserProject == null)
         return;
      if (this.userProject == null)
         this.userProject = new java.util.HashSet<UserProject>();
      if (!this.userProject.contains(newUserProject))
         this.userProject.add(newUserProject);
   }
   
   /** @pdGenerated default remove
     * @param oldUserProject */
   public void removeUserProject(UserProject oldUserProject) {
      if (oldUserProject == null)
         return;
      if (this.userProject != null)
         if (this.userProject.contains(oldUserProject))
            this.userProject.remove(oldUserProject);
   }
   
   /** @pdGenerated default removeAll */
   public void removeAllUserProject() {
      if (userProject != null)
         userProject.clear();
   }

}