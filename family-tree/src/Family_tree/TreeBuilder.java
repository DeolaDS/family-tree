/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Family_tree;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

/**
 *
 * title: FamilyTreeBuilder
 * purpose: Class that creates the tree view of family members
 * 
 */
public class TreeBuilder {
    TreeView<Member> familyView; //family of member objects
    TreeItem<Member> rootItem; 

    public TreeView<Member> getFamilyView() {
        return familyView;
    }

    public TreeItem<Member> getRootItem() {
        return rootItem;
    }

    public void setFamilyView(TreeView<Member> familyView) {
        this.familyView = familyView;
    }

    public void setRootItem(TreeItem<Member> rootItem) {
        this.rootItem = rootItem;
    }
    
    /**
     * 
     * @param rootMember
     * @return familyView
     * description : sets rootMember as the starting of the tree and adds relative members
     * also sets 'dummy member' as labels - Spouse, Parent, Child, Grandchild
     */
    public TreeView<Member> generateTree(Member rootMember){
        rootItem = new TreeItem<>(rootMember);
        familyView = new TreeView<>(rootItem);
        
        try{
            //spouse
            if (rootMember.getSpouse()!=null) {
                TreeItem<Member> spouseLabel = new TreeItem<>(new Member("Spouse",""));
                rootItem.getChildren().add(spouseLabel);
                TreeItem<Member> spouse = new TreeItem(rootMember.getSpouse());
                spouseLabel.getChildren().add(spouse);
            }

            //children
            if(!rootMember.getMemberChildren().isEmpty()){
                TreeItem<Member> childLabel = new TreeItem<>(new Member("Children",""));
                for(Member m: rootMember.getMemberChildren()){
                    TreeItem<Member> child = new TreeItem<>(m);
                    childLabel.getChildren().add(child);
                    TreeItem<Member> gcLabel = new TreeItem<>(new Member("Grand","Children"));

                    if(!m.getMemberChildren().isEmpty()){
                        child.getChildren().add(gcLabel);
                        for(Member gc: m.getMemberChildren()){
                            TreeItem<Member> gchild = new TreeItem<>(gc);
                            gcLabel.getChildren().add(gchild);
                        }
                    }
                }
            rootItem.getChildren().add(childLabel);
            }

            //parents
            if (!rootMember.getParents().isEmpty()) {
                TreeItem<Member> parentLabel = new TreeItem<>(new Member("Parents",""));

                for (Member p : rootMember.getParents()) {
                    TreeItem<Member> parent = new TreeItem<>(p);
                    parentLabel.getChildren().add(parent);
                }
            rootItem.getChildren().add(parentLabel);
            }
        }catch(Exception e){
            System.out.println("Tree Error: " + e.getMessage());
        }
        
        rootItem.setExpanded(true);
        return familyView;
        
    }

}//end class