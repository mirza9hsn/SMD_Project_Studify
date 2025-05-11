package com.handlandmarker.MainPages;

import static com.handlandmarker.LocalDatabase.MyDatabaseHelperKt.getCurrentTimeAsString;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.common.base.Strings;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.handlandmarker.accets.CurrentUser;
import com.handlandmarker.accets.Message;
import com.handlandmarker.accets.My_Group;
import com.handlandmarker.accets.Users;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class FirebaseHelper {
    public String Member_presenting = "Member_Presenting";
    public String Member_presenting_ID = "Member_ID";
    public FirebaseFirestore db_user;
    public FirebaseFirestore db_grp;
    public String _Email = "Email";
    public String _Name = "Name";

    public String _Users = "USERS";

    public String _Groups = "GROUPS";

    public String _Group_admin = "Group_Admin";

    public String _Group_Members = "Group_Members";

    public String Group_Name = "Group_Name";

    public String Members_Present = "Users_Joined";
    public String PrivacyAdd = "Group_Privacy_Add";

    public String privacyMessage = "Group_Privacy_Message";

    public String Text_Inbox = "Group_Text_Inbox";
    public String Voice_Box = "Group_VoiceBox";

    public String _Vedio_Inbox = "Group_Vedio_Inbox";

    public String _ScreenShare = "Group_Screen_Share";
    public String _Message_Count = "Message_Count";


    public FirebaseHelper() {
        db_user = FirebaseFirestore.getInstance();
        db_grp = FirebaseFirestore.getInstance();
    }


    public void addNewUser(String userId, String name, String email) {
        Map<String, Object> user = new HashMap<>();
        user.put(_Name, name);
        user.put(_Email, email);
        ArrayList<String> arr = new ArrayList<>();
        user.put(Group_Name, arr);

        DocumentReference docRef = db_user.collection(_Users).document(userId);
        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                if (task.getResult().exists()) {
                    System.out.println("Document exists!");
                } else {
                    db_user.collection(_Users).document(userId).set(user)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d("Firebase", "User added successfully!");
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.e("Firabes", "Error adding user: " + e.getMessage());
                                }
                            });
                }
    }
        });
    }

    // Add more methods for querying, updating, and deleting dat

    public void AddNewGroup(String Group_Name1, int privacy_Add, int privacy_Message) {


        Map<String, Object> user = new HashMap<>();
        user.put(Group_Name, Group_Name1);
        ArrayList<String> arr =new ArrayList<>();
        arr.add(CurrentUser.globalVariable.getUserID());
        user.put(_Group_admin, arr);
        ArrayList<String> arr1 =new ArrayList<>();
        arr1.add(CurrentUser.globalVariable.getUserID());
        user.put(_Group_Members,arr1);
        user.put(PrivacyAdd, privacy_Add);
        user.put(privacyMessage, privacy_Message);
        user.put(_Message_Count,0);


        DocumentReference da = db_grp.collection(_Groups).document();
        String groupID = da.getId();


        db_grp.collection(_Groups).document(groupID).set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("Firebase", "User added successfully!");
                        Map<String, Object> VoiceCha = new HashMap<>();
                        ArrayList<Strings> arr = new ArrayList<>();
                        VoiceCha.put(Members_Present, arr);
                        db_grp.collection(_Groups).document(groupID).collection(Voice_Box).document(Voice_Box + Group_Name1).set(VoiceCha)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Log.d("Firebase", "User added successfully!");
                                        Map<String, Object> TextCha = new HashMap<>();
                                        TextCha.put("UserID", "");
                                        TextCha.put("Text", "Hello MY name is Haris");
                                        LocalDateTime date = LocalDateTime.now();
                                        String time = getCurrentTimeAsString(date);
                                        TextCha.put("Time", time);
                                        db_grp.collection(_Groups).document(groupID).collection(Text_Inbox).document("0").set(TextCha)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void unused) {

                                                        Log.d("Firebase", "User added successfully!");
                                                        Map<String, Object> VedioCha = new HashMap<>();
                                                        ArrayList<Strings> arr = new ArrayList<>();
                                                        VedioCha.put(Members_Present, arr);
                                                        db_grp.collection(_Groups).document(groupID).collection(_Vedio_Inbox).document(_Vedio_Inbox + Group_Name1).set(VedioCha)
                                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                    @Override
                                                                    public void onSuccess(Void unused) {
                                                                        Log.d("Firebase", "User added successfully!");
                                                                        Map<String, Object> ScreenShareCha = new HashMap<>();
                                                                        ArrayList<Strings> arr = new ArrayList<>();
                                                                        ScreenShareCha.put(Members_Present, arr);
                                                                        ScreenShareCha.put(Member_presenting,"NULL");
                                                                        ScreenShareCha.put(Member_presenting_ID,-1);
                                                                        db_grp.collection(_Groups).document(groupID).collection(_ScreenShare).document(_ScreenShare+Group_Name1).set(ScreenShareCha)
                                                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                    @Override
                                                                                    public void onSuccess(Void unused) {

                                                                                    }
                                                                                });
                                                                    }
                                                                });
                                                    }
                                                });
                                    }
                                });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("Firabes", "Error adding user: " + e.getMessage());
                    }
                });

        db_user.collection(_Users).document(CurrentUser.globalVariable.getUserID()).get()
                .addOnSuccessListener(documentSnapshot -> {
                    Map<String, Object> existingGroups1 = documentSnapshot.getData();
                    ArrayList<String> existingGroups = (ArrayList<String>) existingGroups1.get(Group_Name);
                    if (existingGroups == null) {
                        existingGroups = new ArrayList<>();
                    }
                    existingGroups.add(groupID);

                    // Update the user document with the updated groups list
                    db_user.collection(_Users).document(CurrentUser.globalVariable.getUserID())
                            .update(Group_Name, existingGroups)
                            .addOnSuccessListener(aVoid1 -> Log.d("Firebase", "User document updated successfully!"))
                            .addOnFailureListener(e -> Log.e("Firebase", "Error updating user document: " + e.getMessage()));
                })
                .addOnFailureListener(e -> Log.e("Firebase", "Error fetching user document: " + e.getMessage()));
    }

    public void getName(String id, OnNameFetchedListener listener) {
        db_user.collection(_Users).document(id).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        String name = (String) documentSnapshot.get(_Name);
                        listener.onNameFetched(name);
                    } else {
                        listener.onNameFetched(null); // Document with the provided ID does not exist
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("Firebase", "Error fetching name: " + e.getMessage());
                    listener.onNameFetched(null); // Failed to fetch name
                });
    }



    public void loadGroupsData(OnGroupsFetchedListener listener) {
        ArrayList<My_Group> groupsList = new ArrayList<>();
        for (String Group_ID : CurrentUser.globalVariable.getGroups()) {
            db_grp.collection(_Groups).document(Group_ID).get()
                    .addOnSuccessListener(document -> {

                            if (document.exists()) {
                                Map<String, Object> groupData = document.getData();
                                // Extract data from the map and create My_Group objects
                                String groupID = document.getId();
                                String groupName = (String) groupData.get(Group_Name);
                                ArrayList<String> arr = (ArrayList<String>) groupData.get(_Group_Members);
                                ArrayList<String> Admin = (ArrayList<String>) groupData.get(_Group_admin);
                                long per_Add1 = (long) groupData.get(PrivacyAdd);
                                long per_Mess1 = (long) groupData.get(privacyMessage);
                                long mc = (long) groupData.get(_Message_Count);
                                int per_Add = (int) per_Add1;
                                int per_Mess = (int)per_Mess1;
                                int mcc= (int)mc;

                                // Extract other fields as needed

                                // Create My_Group object and add it to the list
                                My_Group group = new My_Group(groupID, groupName, arr, Admin, per_Add, per_Mess,mcc);
                                groupsList.add(group);
                                listener.onGroupsFetched(group);
                            }
                        // Do something with the groupsList, like updating UI
                    })
                    .addOnFailureListener(e -> {
                        Log.e("Firebase", "Error loading groups data: " + e.getMessage());
                        // Handle failure, such as showing an error message to the user
                    });
        }

    }




    interface OnGroupsFetchedListener {
        void onGroupsFetched(My_Group groups);
    }

    void LoadGroups() {
        loadGroupsData(new OnGroupsFetchedListener() {
            @Override
            public void onGroupsFetched(My_Group groups) {
                if (groups != null) {
                        boolean flag = false;
                        for (My_Group g1 : CurrentUser.Groups) {
                            if (g1.getGroupID().contains(groups.getGroupID())) {
                                flag =true;
                            }
                        }
                        if(flag==false)
                        {
                            CurrentUser.Groups.add(groups);
                            MainActivity.adapter.notifyItemInserted(CurrentUser.Groups.size() - 1);
                        }


                } else {
                    // Handle case where groups could not be fetched
                    Log.e("Firebase", "Failed to fetch groups");
                }
            }
        });

    }
    // Define an interface for the callback
    interface OnNameFetchedListener {
        void onNameFetched(String name);
    }

    //------------------User Data

    interface OnGroups_From_User {
        void onGroupsFetched(ArrayList<String> groups, String Name);
    }
    public void getGroupsForMember(String userID, OnGroups_From_User listener) {
        db_user.collection(_Users).document(userID).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        String name = (String) documentSnapshot.get(_Name);
                        ArrayList<String> groupIDs = (ArrayList<String>) documentSnapshot.get(Group_Name);
                        listener.onGroupsFetched(groupIDs,name);
                    } else {
                        listener.onGroupsFetched(null,null); // User document with the provided ID does not exist
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("Firebase", "Error fetching groups for member: " + e.getMessage());
                    listener.onGroupsFetched(null,null); // Failed to fetch groups for the member
                });
    }

    public void LoadGroups_For_User(String userID)
    {
        getGroupsForMember(userID, new OnGroups_From_User() {
            @Override
            public void onGroupsFetched(ArrayList<String> groups,String name) {
                if (groups != null) {
                    CurrentUser.globalVariable.setGroups(groups);
                    CurrentUser.globalVariable.setUserName(name);
                    LoadGroups();
                    // Groups fetched successfully, do something with them
                } else {
                    // Handle case where groups could not be fetched for the member
                    Log.e("Firebase", "Failed to fetch groups for member");
                }
            }
        });
    }


    public void addUserToGroup(String groupID) {
        // Add user to the group's member list
        DocumentReference groupRef = db_grp.collection(_Groups).document(groupID);
        groupRef.get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                ArrayList<String> members = (ArrayList<String>) documentSnapshot.get(_Group_Members);
                if (members != null) {
                    members.add(CurrentUser.globalVariable.getUserID());
                    groupRef.update(_Group_Members, members)
                            .addOnSuccessListener(aVoid -> Log.d("Firebase", "User added to group successfully"))
                            .addOnFailureListener(e -> Log.e("Firebase", "Error adding user to group: " + e.getMessage()));
                }
            } else {
                Log.e("Firebase", "Group document does not exist");
            }
        }).addOnFailureListener(e -> Log.e("Firebase", "Error fetching group document: " + e.getMessage()));

        // Add groupID to the user's group list
        db_user.collection(_Users).document(CurrentUser.globalVariable.getUserID()).get()
                .addOnSuccessListener(documentSnapshot -> {
                    Map<String, Object> existingGroups1 = documentSnapshot.getData();
                    ArrayList<String> existingGroups = (ArrayList<String>) existingGroups1.get(Group_Name);
                    if (existingGroups == null) {
                        existingGroups = new ArrayList<>();
                    }
                    existingGroups.add(groupID);

                    // Update the user document with the updated groups list
                    db_user.collection(_Users).document(CurrentUser.globalVariable.getUserID())
                            .update(Group_Name, existingGroups)
                            .addOnSuccessListener(aVoid1 -> Log.d("Firebase", "User document updated successfully!"))
                            .addOnFailureListener(e -> Log.e("Firebase", "Error updating user document: " + e.getMessage()));
                })
                .addOnFailureListener(e -> Log.e("Firebase", "Error fetching user document: " + e.getMessage()));
    }


    interface OnMessageReceivedListener {
        void onMessageReceived(String userID, String message, String time,int Count);
    }

    public void listenForGroupChats(String groupID, OnMessageReceivedListener listener,int Last_Message_count) {
        db_grp.collection(_Groups).document(groupID).collection(Text_Inbox)
                .addSnapshotListener((snapshot, e) -> {
                    if (e != null) {
                        Log.e("Firebase", "Listen failed: " + e.getMessage());
                        return;
                    }

                    if (snapshot != null && !snapshot.isEmpty()) {
                        for (DocumentSnapshot doc : snapshot.getDocuments()) {
                            // Extract message data and pass it to the listener
                            String userID = doc.getString("UserID");
                            String message = doc.getString("Text");
                            String time = doc.getString("Time");
                            listener.onMessageReceived(userID, message, time,Integer.parseInt(doc.getId()));
                        }
                    } else {
                        Log.d("Firebase", "No messages");
                    }
                });
    }

    public void sendMessageToGroup(String groupID, String message, int Count) {
        // Create a map with message data
        Map<String, Object> messageData = new HashMap<>();
        messageData.put("UserID", CurrentUser.globalVariable.getUserID());
        messageData.put("Text", message);
        messageData.put("Time", getCurrentTimeAsString(LocalDateTime.now()));

        String s = String.valueOf(Count);
        // Add message to the Text_inbox collection
        db_grp.collection(_Groups).document(groupID).collection(Text_Inbox).document(s)
                .set(messageData)
                .addOnSuccessListener(documentReference -> Log.d("Firebase", "Message sent successfully"))
                .addOnFailureListener(e -> Log.e("Firebase", "Error sending message: " + e.getMessage()));
        HashMap<String,Object>  m = new HashMap<String, Object>();
        m.put(_Message_Count,Count+1);
        db_grp.collection(_Groups).document(groupID).update(m);

    }

    public interface OnUserJoinedListener {
        void onUserJoined(String userID);
        void onUserLeft(ArrayList<String> userID);
    }
    public void listenForUserJoined(String groupID, String group_Name,String Channel ,ArrayList<String> UserPresent,OnUserJoinedListener listener) {
        db_grp.collection(_Groups).document(groupID).collection(Channel).document(Channel+group_Name)
                .addSnapshotListener((snapshot, e) -> {
                    if (e != null) {
                        Log.e("Firebase", "Listen failed: " + e.getMessage());
                        return;
                    }

                    if (snapshot != null ) {
                        for (String str : (ArrayList<String>) snapshot.get(Members_Present)) {
                            // Extract user joined data and pass it to the listener
                            String userID = str; // Assuming the user ID is the document ID
                            if(!UserPresent.contains(userID))
                            {
                                listener.onUserJoined(userID);
                            }
                        }
                        ArrayList<String> prsent = (ArrayList<String>) snapshot.get(Members_Present);
                        ArrayList<String> total = new ArrayList<>();
                        total.addAll(UserPresent);
                        boolean left = false;
                        for (String str: UserPresent)
                        {
                            if(!prsent.contains(str))
                            {
                                left = true;
                                total.remove(str);
                            }
                        }
                        if(left)
                        {
                            listener.onUserLeft(total);
                        }
                    } else {
                        Log.d("Firebase", "No users joined");
                    }
                });
    }


    public void AddUserToGroupVoiceChat(String group_ID,String groupname,String chanal)
    {
        DocumentReference voiceChatGroupRef = db_grp.collection(_Groups)
                .document(group_ID)
                .collection(chanal)
                .document(chanal + groupname);

        // Update the Members_Present field with the user's ID
        voiceChatGroupRef.get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                ArrayList<String> membersPresent = (ArrayList<String>) documentSnapshot.get(Members_Present);
                if (membersPresent != null) {
                    // Add the user's ID to the list of members present
                    membersPresent.add(CurrentUser.globalVariable.getUserID());
                    // Update the document with the updated list of members present
                    voiceChatGroupRef.update(Members_Present, membersPresent)
                            .addOnSuccessListener(aVoid -> Log.d("Firebase", "User added to voice chat group successfully"))
                            .addOnFailureListener(e -> Log.e("Firebase", "Error adding user to voice chat group: " + e.getMessage()));
                }
            } else {
                Log.e("Firebase", "Voice chat group document does not exist");
            }
        }).addOnFailureListener(e -> Log.e("Firebase", "Error fetching voice chat group document: " + e.getMessage()));
    }

    public void removeUserFromGroupVoiceChat(String groupID, String groupname, String Chanal) {
        if (groupID != null && groupname != null) {
            // Get a reference to the voice chat group document
            DocumentReference voiceChatGroupRef = db_grp.collection(_Groups)
                    .document(groupID)
                    .collection(Chanal)
                    .document(Chanal + groupname);

            voiceChatGroupRef.get().addOnSuccessListener(documentSnapshot -> {
                if (documentSnapshot.exists()) {
                    ArrayList<String> membersPresent = (ArrayList<String>) documentSnapshot.get(Members_Present);
                    if (membersPresent != null) {
                        // Add the user's ID to the list of members present
                         membersPresent.remove(CurrentUser.globalVariable.getUserID());
                            // Update the document with the updated list of members present
                        voiceChatGroupRef.update(Members_Present, membersPresent)
                                .addOnSuccessListener(aVoid -> Log.d("Firebase", "User removed from voice chat group successfully"))
                                .addOnFailureListener(e -> Log.e("Firebase", "Error removing user from voice chat group: ", e));
                        }
                } else {
                    Log.e("Firebase", "Voice chat group document does not exist");
                }
            }).addOnFailureListener(e -> Log.e("Firebase", "Error fetching voice chat group document: ", e));
        } else {
            Log.e("Firebase", "Invalid group ID or user ID");
        }
    }

    public void listenForMemberPresenting(String groupID, String groupname, OnMemberPresentingListener listener) {
        DocumentReference voiceChatGroupRef = db_grp.collection(_Groups)
                .document(groupID)
                .collection(_ScreenShare)
                .document(_ScreenShare + groupname);

        voiceChatGroupRef.addSnapshotListener((documentSnapshot, e) -> {
            if (e != null) {
                Log.e("Firebase", "Listen failed: " + e.getMessage());
                return;
            }

            if (documentSnapshot != null && documentSnapshot.exists()) {
                // Check if the Member_presenting field exists
                if (documentSnapshot.contains(Member_presenting)) {
                    // Get the value of Member_presenting field
                    String presentingUserID = documentSnapshot.getString(Member_presenting);
                    double id = documentSnapshot.getDouble(Member_presenting_ID);
                    int idd= (int) id;
                    listener.onMemberPresenting(presentingUserID,idd);
                } else {
                    // The Member_presenting field doesn't exist or is null
                    listener.onMemberPresenting("NULL", -1);
                }
            } else {
                Log.d("Firebase", "Voice chat group document does not exist");
            }
        });
    }

    public interface OnMemberPresentingListener {
        void onMemberPresenting(String presentingUserID, int USD_REMORT);
    }

    public void addUserIDBroadcast(int IDD) {
        String groupID = CurrentUser.CurrentGroup.getGroupID();
        String groupName = CurrentUser.CurrentGroup.getGroupName();

        if (groupID != null && groupName != null) {
            DocumentReference voiceChatGroupRef = db_grp.collection(_Groups)
                    .document(groupID)
                    .collection(_ScreenShare)
                    .document(_ScreenShare + groupName);

            voiceChatGroupRef.get().addOnSuccessListener(documentSnapshot -> {
                String existingMembersPresent = documentSnapshot.getString(Member_presenting);

                if(existingMembersPresent.contains( "NULL" ))
                {
                    // Update the MemberPresenting field
                    String presentingUserID = CurrentUser.globalVariable.getUserID();
                    if (presentingUserID != null) {
                        voiceChatGroupRef.update(Member_presenting, presentingUserID)
                                .addOnSuccessListener(aVoid -> Log.d("Firebase", "MemberPresenting updated successfully"))
                                .addOnFailureListener(e -> Log.e("Firebase", "Error updating MemberPresenting: " + e.getMessage()));
                        voiceChatGroupRef.update(Member_presenting_ID, IDD)
                                .addOnSuccessListener(aVoid -> Log.d("Firebase", "MemberPresenting updated successfully"))
                                .addOnFailureListener(e -> Log.e("Firebase", "Error updating MemberPresenting: " + e.getMessage()));
                    }
                }
            }).addOnFailureListener(e -> Log.e("Firebase", "Error getting document: " + e.getMessage()));

            }
    }

    public void RemoveUserIDBroadcast()
    {
        String groupID = CurrentUser.CurrentGroup.getGroupID();
        String groupName = CurrentUser.CurrentGroup.getGroupName();

        if (groupID != null && groupName != null) {
            DocumentReference voiceChatGroupRef = db_grp.collection(_Groups)
                    .document(groupID)
                    .collection(_ScreenShare)
                    .document(_ScreenShare + groupName);

            voiceChatGroupRef.get().addOnSuccessListener(documentSnapshot -> {
                String existingMembersPresent = documentSnapshot.getString(Member_presenting);

                if(existingMembersPresent != "NULL" )
                {
                    // Update the MemberPresenting field
                    String presentingUserID = CurrentUser.globalVariable.getUserID();
                    if (presentingUserID.contains(existingMembersPresent)) {
                        voiceChatGroupRef.update(Member_presenting, "NULL")
                                .addOnSuccessListener(aVoid -> Log.d("Firebase", "MemberPresenting updated successfully"))
                                .addOnFailureListener(e -> Log.e("Firebase", "Error updating MemberPresenting: " + e.getMessage()));
                        voiceChatGroupRef.update(Member_presenting_ID, -1)
                                .addOnSuccessListener(aVoid -> Log.d("Firebase", "MemberPresenting updated successfully"))
                                .addOnFailureListener(e -> Log.e("Firebase", "Error updating MemberPresenting: " + e.getMessage()));
                    }
                }
            }).addOnFailureListener(e -> Log.e("Firebase", "Error getting document: " + e.getMessage()));

        }
    }

    // Define an interface for the callback
    public interface OnDocumentIDsFetchedListener {
        void onDocumentIDsFetched(ArrayList<String> documentIDs);
    }

    // Method to fetch all document IDs in the _Groups collection
    public void loadAllGroupDocumentIDs(OnDocumentIDsFetchedListener listener) {
        db_grp.collection(_Groups).get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    ArrayList<String> documentIDs = new ArrayList<>();
                    for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                        if(CurrentUser.checkGroup(document.getId()))
                                CurrentUser.AllGroupIDs.add(document.getId());

                    }
                    listener.onDocumentIDsFetched(documentIDs);
                })
                .addOnFailureListener(e -> {
                    Log.e("Firebase", "Error fetching document IDs: " + e.getMessage());
                    listener.onDocumentIDsFetched(null); // Handle failure
                });
    }

    public interface OnGroupNameFetchedListener {
        void onGroupNameFetched(String groupName);
    }

    // Method to fetch a group's name by its ID
    public void getGroupNameByID(String groupID, OnGroupNameFetchedListener listener) {
        db_grp.collection(_Groups).document(groupID).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        String groupName = documentSnapshot.getString(Group_Name);
                        listener.onGroupNameFetched(groupName);
                    } else {
                        listener.onGroupNameFetched(null); // Group document with the provided ID does not exist
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("Firebase", "Error fetching group name: " + e.getMessage());
                    listener.onGroupNameFetched(null); // Failed to fetch group name
                });
    }
}

