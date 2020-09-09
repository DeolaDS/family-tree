/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Family_tree;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Paths;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * title: GUI
 * description: Class that contains GUI components and layout
 */
public class GUI extends Application{
    TreeView<Member> tree;
    private final FileChooser fileChooser = new FileChooser();
    private final BorderPane borderPane = new BorderPane();
    Alert warning = new Alert(AlertType.WARNING);
    Alert msg = new Alert(AlertType.INFORMATION);
    
    ObservableList<String> genderList = FXCollections.observableArrayList("Female", "Male");
    ObservableList<String> relativeList = FXCollections.observableArrayList("Mother", "Father", "Child", "Spouse");
    ComboBox<String> gender = new ComboBox<>(genderList);
    ComboBox<String> newMemGen = new ComboBox<>(genderList);
    ComboBox<String> relative = new ComboBox<>(relativeList);
    /*
        CREATING TEXTFIELDS AND LABELS
    */
    Label heading = new Label("Family Tree");
    Label lbName = new Label("First Name: ");
    Label lbSur = new Label("Last Name: ");
    Label lbMaidnNm = new Label("Maiden Name: ");
    Label lbGen = new Label("Gender: ");
    Label lbStNum = new Label("Street Number: ");
    Label lbStName = new Label("Street Name: ");
    Label lbSub = new Label("Suburb: ");
    Label lbPstCd = new Label("Post Code: ");
    Label lbChild = new Label("Children: ");
    Label lbSpouse = new Label("Spouse: ");
    Label lbMother = new Label("Mother: ");
    Label lbFather = new Label("Father: ");
    Label lbDesc = new Label("Description: ");
    Label lbDet = new Label("Details");
    Label lbAddrDet = new Label("Address");

    TextField txtName = new TextField();
    TextField txtSur = new TextField();
    TextField txtMaiden = new TextField();
    TextField txtStNum = new TextField();
    TextField txtStName = new TextField();
    TextField txtSub = new TextField();
    TextField txtPstCd = new TextField();
    TextArea taDesc = new TextArea();
    TextField txtChild = new TextField();
    TextField txtSpouse = new TextField();
    TextField txtMother = new TextField();
    TextField txtFather = new TextField();
    /**
        BUTTONS
    */
    Button loadFam = new Button("Load family");
    Button saveFam = new Button("Save family");
    Button newFam = new Button("New family");
    Button browseBtn = new Button("Browse Member");
    Button viewBtn = new Button("View");
    Button editBtn = new Button("Edit");
    Button updateBtn = new Button("Update");
    Button addRelativeBtn = new Button("Add Relative");
    Button saveRelative = new Button("Save Relative");
    Button clear = new Button("Clear");
    Button addNewMemBtn = new Button("Add Member"); 
    Button closeBtn = new Button("Close"); 
    /*
        LABELS AND TEXTFIELDS FOR WINDOW
    */
    Label newlbName = new Label("First Name: ");
    Label newlbSur = new Label("Last Name: ");
    Label newlbMaidnNm = new Label("Maiden Name: ");
    Label newlbGen = new Label("Gender: ");
    Label newlbStNum = new Label("Street Number: ");
    Label newlbStName = new Label("Street Name: ");
    Label newlbSub = new Label("Suburb: ");
    Label newlbPstCd = new Label("Post Code: ");
    Label newlbDesc = new Label("Description: ");
    Label newlbDet = new Label("Details");
    Label newlbAddrDet = new Label("Address");
    
    TextField newtxtName = new TextField();
    TextField newtxtSur = new TextField();
    TextField newtxtMaiden = new TextField();
    TextField newtxtStNum = new TextField();
    TextField newtxtStName = new TextField();
    TextField newtxtSub = new TextField();
    TextField newtxtPstCd = new TextField();
    TextArea newtaDesc = new TextArea();
   
    @Override
    public void start(Stage primaryStage) {
        gender.setPromptText("Select Gender");
        
        TreeBuilder ftb = new TreeBuilder();
        Member doe = new Member("Joe","Doe");
        doe.popMember();
        tree = ftb.generateTree(doe);

        warning.setTitle("Error Dialog");
        msg.setTitle("Information Dialog");

        mode(false); //default in view mode
        browseBtn.setOnAction((ActionEvent event) -> {
            try{
                Member selectedFam = tree.getSelectionModel().getSelectedItem().getValue();
                tree = ftb.generateTree(selectedFam); //creates a tree with this as root
                borderPane.setLeft(tree);
                
                tree.setOnMouseClicked((MouseEvent event1) -> {
                    try {
                        if (tree.getSelectionModel().getSelectedItem().getValue() != null) {
                            Member selectedFam1 = tree.getSelectionModel().getSelectedItem().getValue();
                            txtName.setText(selectedFam1.getFirstName());
                            txtSur.setText(selectedFam1.getSurname());
                            txtMaiden.setText(selectedFam1.getMaidenName());
                            gender.setValue(selectedFam1.getGender());
                            taDesc.setText(selectedFam1.getDescription());
                           
                            String children="";
                            for(int i = 0;i<selectedFam1.getMemberChildren().size();i++)
                            {
                                children += selectedFam1.getMemberChildren().get(i).getFirstName() + ", ";
                            }
                            txtChild.setText(children);
                            
                            if(selectedFam1.getMother()!=null)
                                txtMother.setText(selectedFam1.getMother().getFirstName());
                            else
                                txtMother.setText("");
                          
                            if(selectedFam1.getFather()!=null)
                                txtFather.setText(selectedFam1.getFather().getFirstName());
                            else
                                txtFather.setText("");
                            
                            if(selectedFam1.getSpouse()!=null)
                                txtSpouse.setText(selectedFam1.getSpouse().getFirstName());
                            else
                                txtSpouse.setText("");

                            txtStName.setText(selectedFam1.getAddress().getStreetName());
                            txtStNum.setText("" + selectedFam1.getAddress().getStreetNum());
                            txtSub.setText(selectedFam1.getAddress().getSuburb());
                            txtPstCd.setText("" + selectedFam1.getAddress().getPostCode());
                        } else {
                            warning.setContentText("member is null");
                            warning.show();
                        }
                    }catch (NullPointerException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                });
            }catch(Exception e){
                System.out.println("Error: " + e.getMessage());
            }
        });
        
        tree.setOnMouseClicked((MouseEvent event) -> {
            try {
                if (tree.getSelectionModel().getSelectedItem().getValue() != null) {
                    Member selectedFam = tree.getSelectionModel().getSelectedItem().getValue();
                    txtName.setText(selectedFam.getFirstName());
                    txtSur.setText(selectedFam.getSurname());
                    txtMaiden.setText(selectedFam.getMaidenName());
                    gender.setValue(selectedFam.getGender());
                    taDesc.setText(selectedFam.getDescription());
                    
                    String children="";
                    for(int i = 0;i<selectedFam.getMemberChildren().size();i++)
                    {
                        children+= selectedFam.getMemberChildren().get(i).getFirstName() + ", " ;
                    }
                    txtChild.setText(children);
                    
                    if(selectedFam.getMother()!=null)
                        txtMother.setText(selectedFam.getMother().getFirstName());
                    else
                        txtMother.setText("");

                    if(selectedFam.getFather()!=null)
                        txtFather.setText(selectedFam.getFather().getFirstName());
                    else
                        txtFather.setText("");

                    if(selectedFam.getSpouse()!=null)
                        txtSpouse.setText(selectedFam.getSpouse().getFirstName());
                    else
                        txtSpouse.setText("");
                  
                    txtStName.setText(selectedFam.getAddress().getStreetName());
                    txtStNum.setText("" + selectedFam.getAddress().getStreetNum());
                    txtSub.setText(selectedFam.getAddress().getSuburb());
                    txtPstCd.setText("" + selectedFam.getAddress().getPostCode());  
                } else {
                    System.out.println("value(member) is null");
                }
            } catch (NullPointerException e) {
                System.out.println("Error: " + e.getMessage());
            }
        });

        loadFam.setOnAction((ActionEvent event) -> {
            try{
                fileChooser.setTitle("Open a family");
                //filter the files to get only .member files
                fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Member Files", "*.member"));
                String currentPath = Paths.get(".").toAbsolutePath().normalize().toString();
                //sets 'families' folder as the initial directory
                String currPath = currentPath + "\\families";
                fileChooser.setInitialDirectory(new File(currPath));
                //gets the file selected 
                File selectedFile = fileChooser.showOpenDialog(primaryStage);
                
                if (selectedFile != null) {
                    Member fm = loadFamily(selectedFile);
                    System.out.println(fm);
                    tree = ftb.generateTree(fm);
                    borderPane.setLeft(tree);
                    
                } else {
                    System.out.println("File selection cancelled.");
                }
            }catch(Exception e){
                System.out.println("Load error: " + e.getMessage());
            }
        });

        saveFam.setOnAction((ActionEvent event) -> {
            try {
                fileChooser.setTitle("Save your family");
                fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Member Files", "*.member"));
                String currentPath = Paths.get(".").toAbsolutePath().normalize().toString();
                String currPath = currentPath + "\\families";
                fileChooser.setInitialDirectory(new File(currPath));
                File savedFile = fileChooser.showSaveDialog(primaryStage);
                                
                if (tree.getSelectionModel().getSelectedItem().getValue() != null) {
                    Member savedMem = tree.getSelectionModel().getSelectedItem().getValue();
                    System.out.println("Current member :" +savedMem);
                    
                    saveFamily(savedFile, savedMem);
                    msg.setContentText("File saved: " + savedFile.getName());
                    msg.showAndWait();   
                }
            }catch(Exception e){
                System.out.println("Error: "+ e.getMessage() );
            }
        });

        updateBtn.setOnAction((ActionEvent event) -> {            
            try {       
                if (tree.getSelectionModel().getSelectedItem().getValue() != null) {
                    Member selectedFam = tree.getSelectionModel().getSelectedItem().getValue();
                    editDetails(selectedFam);
                    msg.setContentText("Member is updated!");
                    msg.show();
                } else {
                    msg.setContentText("value(member) is null");
                    msg.show();
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        });
        //switches to view mode
        viewBtn.setOnAction((ActionEvent event) -> {
            mode(false);
        });
        //switches to edit mode
        editBtn.setOnAction((ActionEvent event) -> {
            mode(true);
        });
        
        //calls function to clear input fields
        clear.setOnAction((ActionEvent event) -> {
            clearMain();
        });

        //creates a new window for adding a new family
        newFam.setOnAction((ActionEvent event) -> {
            newMemGen.setPromptText("Select Gender");
            GridPane addMemberPane = createNewFamilyGUI();
            Scene secondScene = new Scene(addMemberPane, 230, 100);
            // New window (Stage)
            Stage newFamilyWindow = new Stage();
            newFamilyWindow.setTitle("Create a Family");
            newFamilyWindow.setScene(secondScene);
            newFamilyWindow.setMinWidth(450);
            newFamilyWindow.setMinHeight(560);
            newFamilyWindow.initModality(Modality.WINDOW_MODAL);  // Specifies the modality for new window.
            newFamilyWindow.initOwner(primaryStage); // Specifies the owner Window (parent) for new window
            newFamilyWindow.show();
            
            //closes the window
            closeBtn.setOnAction((ActionEvent e) -> {
                newFamilyWindow.close();
            });
        });
        
        addNewMemBtn.setOnAction((ActionEvent e) -> {
            Member newMem = addNewFamily();
            tree = ftb.generateTree(newMem);
            borderPane.setLeft(tree);

            tree.setOnMouseClicked((MouseEvent event1) -> {
               try {
                   if (tree.getSelectionModel().getSelectedItem().getValue() != null) {
                       Member selectedFam1 = tree.getSelectionModel().getSelectedItem().getValue();
                       txtName.setText(selectedFam1.getFirstName());
                       txtSur.setText(selectedFam1.getSurname());
                       txtMaiden.setText(selectedFam1.getMaidenName());
                       gender.setValue(selectedFam1.getGender());
                       taDesc.setText(selectedFam1.getDescription());

                       txtStName.setText(selectedFam1.getAddress().getStreetName());
                       txtStNum.setText("" + selectedFam1.getAddress().getStreetNum());
                       txtSub.setText(selectedFam1.getAddress().getSuburb());
                       txtPstCd.setText("" + selectedFam1.getAddress().getPostCode());

                   } else {
                       warning.setContentText("member is null");
                       warning.show();
                       System.out.println("value(member) is null");
                   }
               }catch (NullPointerException exc) {
                   System.out.println("Error: " + exc.getMessage());
               }
            });    
            clearMain();
        });
        
        // creates a new new window for adding a relative
        addRelativeBtn.setOnAction((ActionEvent e) -> {
            relative.setPromptText("Select Relative Type");
            clearWindow();
            GridPane addRelativePane = createAddRelativeGUI();
            Scene thirdscene = new Scene(addRelativePane, 230, 100);
            // New window (Stage)
            Stage addRelativeWindow = new Stage();
            addRelativeWindow.setTitle("Add Relative");
            addRelativeWindow.setScene(thirdscene);
            addRelativeWindow.setMinWidth(450);
            addRelativeWindow.setMinHeight(560);
            addRelativeWindow.initModality(Modality.WINDOW_MODAL);  // Specifies the modality for new window.
            addRelativeWindow.initOwner(primaryStage); // Specifies the owner Window (parent) for new window
            addRelativeWindow.show();

            //closes the window
            closeBtn.setOnAction((ActionEvent a) -> {
                addRelativeWindow.close();
            });
        });
        
        //button on the addRelativeWindow that saves the member into the tree
        saveRelative.setOnAction((ActionEvent event) -> {
            Member newMem = addRelative();
            try {
                if (tree.getSelectionModel().getSelectedItem().getValue() != null) {
                    Member selectedRel = tree.getSelectionModel().getSelectedItem().getValue();      
                    System.out.println("Member to add a relative to: "+selectedRel);
                    
                    if(relative.getValue().equals("Select Relative Type")){
                        warning.setContentText("Please select relative type!");
                        warning.show();
                    }
                    else{      
                        selectedRel.addMember(newMem, relative.getValue());  
                    }
                    tree = ftb.generateTree(selectedRel);
                    borderPane.setLeft(tree);

                }else {
                    System.out.println("value(member) is null");
                }
            } catch (Exception e2) {
                System.out.println("Error: " + e2.getMessage());
            } 
            clearWindow();
        });
        
        BorderPane bp = createMainGUI();
        Scene scene = new Scene(bp, 800, 450);

        primaryStage.setTitle("Family Tree");
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(680);
        primaryStage.setMinHeight(720);
        primaryStage.show();
    }//end start
    
    /**
      * 
      * @return m
      * description: Creates a new Member object with the user input from the text field/combo boxes
      */
     public Member addRelative(){
        Member m = new Member();
        int num = 0, pstCode= 0;
        try{
            m.setFirstName(newtxtName.getText());
            m.setSurname(newtxtSur.getText());
            m.setMaidenName(newtxtMaiden.getText());
            m.setGender(newMemGen.getValue());
            m.setDescription(newtaDesc.getText());
            
            if(newtxtStNum.getText()!=null){
                num = Integer.parseInt(newtxtStNum.getText());
            }
            if(newtxtPstCd.getText()!=null){
                pstCode = Integer.parseInt(newtxtPstCd.getText());
            }
            m.setAddress(new Address(num, newtxtStName.getText(), newtxtSub.getText(), pstCode));
            
        } catch (NumberFormatException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return m;

    }
     /**
      * 
      * @return m
      * description: Creates a new Member object with the user input from the text field/combo box
      */
    public Member addNewFamily(){
        Member m = new Member() ;
        try{
            m.setFirstName(newtxtName.getText());
            m.setSurname(newtxtSur.getText());
            m.setMaidenName(newtxtMaiden.getText());
            m.setGender(newMemGen.getValue());
            m.setDescription(newtaDesc.getText());
           
            int num = Integer.parseInt(newtxtStNum.getText());
            int pstCode = Integer.parseInt(newtxtPstCd.getText());
            m.setAddress(new Address(num, newtxtStName.getText(), newtxtSub.getText(), pstCode));
             
            msg.setContentText("A new member is created!");
            msg.show();
        } catch (NumberFormatException e) {
                System.out.println("Error: " + e.getMessage());
            }
        return m;
    }
    /**
     * 
     * @param m 
     * description: Takes in a Member object and sets the new values from the text fields
     */
    public  void editDetails(Member m) {
        int num = 0, pstCode = 0;
        try{
            System.out.println("Editing memeber: "+ m);
            m.setFirstName(txtName.getText());
            m.setSurname(txtSur.getText());
            m.setMaidenName(txtMaiden.getText());
            m.setGender(gender.getValue());
            m.setDescription(taDesc.getText());

            if(m.getAddress() != null) {
                m.getAddress().setStreetNum(Integer.parseInt(txtStNum.getText()));
                m.getAddress().setStreetName(txtStName.getText());
                m.getAddress().setSuburb(txtSub.getText());
                m.getAddress().setPostCode(Integer.parseInt(txtPstCd.getText()));
            }else{
                if(newtxtStNum.getText()!=null){
                    num = Integer.parseInt(newtxtStNum.getText());
                }
                if(newtxtPstCd.getText()!=null){
                    pstCode = Integer.parseInt(newtxtPstCd.getText());
                }
                 m.setAddress(new Address(num, newtxtStName.getText(), newtxtSub.getText(), pstCode));
            }

            if(m.getMother()!= null)
                m.getMother().setFirstName(txtMother.getText());
            else 
                m.setMother(new Member(txtMother.getText(),""));

            if(m.getFather()!= null)
                m.getFather().setFirstName(txtFather.getText());
            else 
               m.setFather(new Member(txtFather.getText(), ""));

            if(m.getSpouse()!= null)
                m.getSpouse().setFirstName(txtSpouse.getText());
            else
                m.setSpouse(new Member(txtSpouse.getText(), "")); 
            
        }catch(NumberFormatException e){
            System.out.println("Error: "+ e.getMessage());
        } 
    }
    /**
     * 
     * @param savedFam
     * @param member 
     * description: Writes the member object to a file 
     */
    public void saveFamily(File savedFam, Member member) {
        
        if (tree != null) {
           String filename = savedFam.getPath(); 
            System.out.println("file name: "+filename);
            try {
                FileOutputStream fos = new FileOutputStream(filename);
               try (ObjectOutputStream oos = new ObjectOutputStream(fos)) {
                   System.out.println("Writing family to a file ...");
                   oos.writeObject(member);
               }
            } catch (IOException ex) {
                System.out.println("IO Exception");
            }catch(Exception e){
                System.out.println("Write Error: " + e.getMessage());
           }
        } else {
            warning.setContentText("No family to save");
            warning.show();
        }

    }//end function
    /**
     * 
     * @param selectedFile
     * @return fm
     * description: Takes in a File object, and reads the Member object
     */
    public  Member loadFamily(File selectedFile) {
        Member fm = null;
        try {
            File fmFile = new File(selectedFile.getPath());
            
            FileInputStream fis = new FileInputStream(fmFile);
            try (ObjectInputStream ois = new ObjectInputStream(fis)) {
                System.out.println("Reading  family from a file ...");
                fm = (Member)ois.readObject();
            }
            msg.setContentText("File Read: " + selectedFile.getName());
            msg.show();
            System.out.println("File read: " + selectedFile.getName());
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(" Read Error: " + e.getMessage());
        }
       return fm;
    }
    /**
     * 
     * @return addRelativePane
     * description: Returns the a GridPane GUI layout for the 'Add Relative' button
     */
    public GridPane createAddRelativeGUI(){
        Label memType = new Label("Member Type");
        GridPane addRelativePane = new GridPane();
        addRelativePane.setHgap(5);
        addRelativePane.setVgap(5);
        addRelativePane.setPadding(new Insets(25, 25, 25, 25));

        HBox btns = new HBox();
        btns.getChildren().addAll(saveRelative, closeBtn);
        btns.setSpacing(10);

        addRelativePane.add(newlbDet, 0, 0);
        addRelativePane.add(newlbName, 0, 1);
        addRelativePane.add(newtxtName, 1, 1);
        addRelativePane.add(newlbSur, 0, 2);
        addRelativePane.add(newtxtSur, 1, 2);
        addRelativePane.add(newlbMaidnNm, 0, 3);
        addRelativePane.add(newtxtMaiden, 1, 3);
        addRelativePane.add(newlbGen, 0, 4);
        addRelativePane.add(newMemGen, 1, 4);
        addRelativePane.add(newlbDesc, 0, 5);
        addRelativePane.add(newtaDesc, 1, 5);

        addRelativePane.add(newlbAddrDet, 0, 10);
        addRelativePane.add(newlbStNum, 0, 11);
        addRelativePane.add(newtxtStNum, 1, 11);
        addRelativePane.add(newlbStName, 0, 12);
        addRelativePane.add(newtxtStName, 1, 12);
        addRelativePane.add(newlbSub, 0, 13);
        addRelativePane.add(newtxtSub, 1, 13);
        addRelativePane.add(newlbPstCd, 0, 14);
        addRelativePane.add(newtxtPstCd, 1, 14);
        addRelativePane.add(memType, 0, 15);
        addRelativePane.add(relative, 1, 15);
        addRelativePane.add(btns, 1, 17);
        
        newtaDesc.setPrefColumnCount(20);
        newtaDesc.setPrefRowCount(10);
        return addRelativePane;    

    }
    /**
     * 
     * @return borderPane
     * description : Returns a BorderPane GUI layout for the main GUI of the program
     */
    public BorderPane createMainGUI(){
        HBox hbox = new HBox();
        hbox.getChildren().addAll(browseBtn, newFam, loadFam, saveFam);
        hbox.setSpacing(10);
        hbox.setPadding(new Insets(10, 20, 10, 20));

        HBox hboxModeBtns = new HBox();
        hboxModeBtns.getChildren().addAll(viewBtn, editBtn);
        hboxModeBtns.setAlignment(Pos.CENTER);
        hboxModeBtns.setSpacing(10);
        hboxModeBtns.setPadding(new Insets(10, 20, 10, 20));
        
        HBox updateBtns = new HBox();
        updateBtns.getChildren().addAll(updateBtn,addRelativeBtn,clear);
        updateBtns.setSpacing(10);
        updateBtns.setPadding(new Insets(10, 20, 10, 20));

        heading.setStyle("-fx-font-size: 20");
        VBox header = new VBox(heading, hboxModeBtns);
        header.setAlignment(Pos.CENTER);
        header.setPadding(new Insets(5));

        lbDet.setStyle("-fx-font-size: 15");
        lbAddrDet.setStyle("-fx-font-size: 15");
        taDesc.setPrefColumnCount(22);
        GridPane gp = new GridPane();
        gp.setHgap(5);
        gp.setVgap(5);
        gp.setPadding(new Insets(25, 25, 25, 25));

        gp.add(lbDet, 0, 0);
        gp.add(lbName, 0, 1);
        gp.add(txtName, 1, 1);
        gp.add(lbSur, 0, 2);
        gp.add(txtSur, 1, 2);
        gp.add(lbMaidnNm, 0, 3);
        gp.add(txtMaiden, 1, 3);
        gp.add(lbGen, 0, 4);
        gp.add(gender, 1, 4);
        gp.add(lbDesc, 0, 5);
        gp.add(taDesc, 1, 5);
        gp.add(lbMother, 0, 6);
        gp.add(txtMother, 1, 6);
        gp.add(lbFather, 0, 7);
        gp.add(txtFather, 1, 7);
        gp.add(lbSpouse, 0, 8);
        gp.add(txtSpouse, 1, 8);
        gp.add(lbChild, 0, 9);
        gp.add(txtChild, 1, 9);

        gp.add(lbAddrDet, 0, 10);
        gp.add(lbStNum, 0, 11);
        gp.add(txtStNum, 1, 11);
        gp.add(lbStName, 0, 12);
        gp.add(txtStName, 1, 12);
        gp.add(lbSub, 0, 13);
        gp.add(txtSub, 1, 13);
        gp.add(lbPstCd, 0, 14);
        gp.add(txtPstCd, 1, 14);
        gp.add(updateBtns, 1, 16);
        
        borderPane.setCenter(gp);
        borderPane.setTop(header);
        borderPane.setLeft(tree);
        borderPane.setBottom(hbox); 
        
        return borderPane;
    }
    /**
     * @return addMemberPane
     * description: Returns a GridPane GUI layout for 'New Family' button
     */
    public GridPane createNewFamilyGUI(){
        GridPane addMemberPane = new GridPane();
        addMemberPane.setHgap(5);
        addMemberPane.setVgap(5);
        addMemberPane.setPadding(new Insets(25, 25, 25, 25));

        HBox btns = new HBox();
        btns.getChildren().addAll(addNewMemBtn, closeBtn);
        btns.setSpacing(10);

        addMemberPane.add(newlbDet, 0, 0);
        addMemberPane.add(newlbName, 0, 1);
        addMemberPane.add(newtxtName, 1, 1);
        addMemberPane.add(newlbSur, 0, 2);
        addMemberPane.add(newtxtSur, 1, 2);
        addMemberPane.add(newlbMaidnNm, 0, 3);
        addMemberPane.add(newtxtMaiden, 1, 3);
        addMemberPane.add(newlbGen, 0, 4);
        addMemberPane.add(newMemGen, 1, 4);
        addMemberPane.add(newlbDesc, 0, 5);
        addMemberPane.add(newtaDesc, 1, 5);

        addMemberPane.add(newlbAddrDet, 0, 10);
        addMemberPane.add(newlbStNum, 0, 11);
        addMemberPane.add(newtxtStNum, 1, 11);
        addMemberPane.add(newlbStName, 0, 12);
        addMemberPane.add(newtxtStName, 1, 12);
        addMemberPane.add(newlbSub, 0, 13);
        addMemberPane.add(newtxtSub, 1, 13);
        addMemberPane.add(newlbPstCd, 0, 14);
        addMemberPane.add(newtxtPstCd, 1, 14);
        addMemberPane.add(btns, 1, 16);
        newtaDesc.setPrefColumnCount(20);
        newtaDesc.setPrefRowCount(10);
        return addMemberPane;    
    }   

    public void mode(Boolean m) {
        txtName.setEditable(m);
        txtSur.setEditable(m);
        txtMaiden.setEditable(m);
        taDesc.setEditable(m);
        
        txtStNum.setEditable(m);
        txtStName.setEditable(m);
        txtSub.setEditable(m);
        txtPstCd.setEditable(m);
        txtChild.setEditable(m);
        txtSpouse.setEditable(m);
        txtMother.setEditable(m);
        txtFather.setEditable(m);

        updateBtn.setDisable(!m);
        addRelativeBtn.setDisable(!m);
        clear.setDisable(!m);
    }

    public void clearMain(){
        txtName.clear();
        txtSur.clear();
        txtMaiden.clear();
        gender.setValue("Select Gender");
        taDesc.clear();
        txtStNum.clear();
        txtStName.clear();
        txtSub.clear();
        txtPstCd.clear();
        txtChild.clear();
        txtSpouse.clear();
        txtMother.clear();
        txtFather.clear();
    }
    
     public void clearWindow(){
        newtxtName.clear();
        newtxtSur.clear();
        newtxtMaiden.clear();
        newMemGen.setValue("Select Gender");
        newtaDesc.clear();
        newtxtStNum.clear();
        newtxtStName.clear();
        newtxtSub.clear();
        newtxtPstCd.clear(); 
        relative.setValue("Select Relative Type");
    }
     
}//end GUI