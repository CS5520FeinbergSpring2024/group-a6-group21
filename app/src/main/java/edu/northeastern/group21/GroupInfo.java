package edu.northeastern.group21;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

class GroupMember {
    private String name;
    private String info;
    private int imageResourceId; // This will reference a drawable resource

    public GroupMember(String name, String info) {
        this.name = name;
        this.info = info;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getInfo() {
        return info;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }
}

public class GroupInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_info);

        // Create your group members
        GroupMember member1 = new GroupMember("Anjing Huang", "-");
        GroupMember member2 = new GroupMember("Jiaxu Zhang", "-");
        GroupMember member3 = new GroupMember("Meng Wang", "-");
        GroupMember member4 = new GroupMember("Xiaoti Hu", "-");

        // Set the data for each card
        setGroupMemberData((CardView) findViewById(R.id.memberCard1), member1);
        setGroupMemberData((CardView) findViewById(R.id.memberCard2), member2);
        setGroupMemberData((CardView) findViewById(R.id.memberCard3), member3);
        setGroupMemberData((CardView) findViewById(R.id.memberCard4), member4);
    }

    private void setGroupMemberData (CardView cardView, GroupMember member){
            ((TextView) cardView.findViewById(R.id.tvMemberName)).setText(member.getName());
            ((TextView) cardView.findViewById(R.id.tvMemberInfo)).setText(member.getInfo());
    }

}

