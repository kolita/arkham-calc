/*ArkhamCalc
Copyright (C) 2011  Matthew Cole

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.*/

package com.kolita.arkhamcalc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.ExpandableListActivity;
import android.os.Bundle;
import android.widget.ExpandableListAdapter;
import android.widget.SimpleExpandableListAdapter;

public class ArkhamCalcHelp extends ExpandableListActivity
{
    private static final String HELP_TOPIC_1 = "Dice / Difficulty";
    private static final String HELP_TOPIC_2 = "Chances";
    private static final String HELP_CONTENT_1 = "Arkham Horror uses dice rolls to determine the outcome of a number of events in the game, including combat and skill checks. Each such event will have a stated difficulty, which is defined as the number of successed required to pass the event.";
    private static final String HELP_CONTENT_2 = "For some events in Arkham Horror, it is possible to attempt the check more than one time. An example is combat - if a monster deals a one point of damage per round of combat, and your character currently has three stamina, you can attempt the combat check three times before your characters is knocked unconscious.";
    
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        
        ExpandableListAdapter adapter = new SimpleExpandableListAdapter
            (this, getGroupData(), R.layout.help_group_row, 
             new String[] { "helpTopic" }, new int[] { R.id.helpTopicTextView }, 
             getChildData(), R.layout.help_child_row,
             new String[] { "helpContent" }, new int[] {R.id.helpContentTextView});
        setListAdapter(adapter);
    }
    
    private List<Map<String, String>> getGroupData()
    {
        List<Map<String, String>> groupList = new ArrayList<Map<String, String>>();
        
        Map<String, String> groupMap1 = new HashMap<String, String>();
        groupMap1.put("helpTopic", HELP_TOPIC_1);
        groupList.add(groupMap1);
        
        Map<String, String> groupMap2 = new HashMap<String, String>();
        groupMap2.put("helpTopic", HELP_TOPIC_2);
        groupList.add(groupMap2);
        
        return groupList;
    }
    
    private List<List<Map<String, String>>> getChildData()
    {
        List<List<Map<String, String>>> childList = new ArrayList<List<Map<String, String>>>();
        
        List<Map<String, String>> childList1 = new ArrayList<Map<String, String>>();
        Map<String, String> childMap1 = new HashMap<String, String>();
        childMap1.put("helpContent", HELP_CONTENT_1);
        childList1.add(childMap1);
        childList.add(childList1);
        
        List<Map<String, String>> childList2 = new ArrayList<Map<String, String>>();
        Map<String, String> childMap2 = new HashMap<String, String>();
        childMap2.put("helpContent", HELP_CONTENT_2);
        childList2.add(childMap2);
        childList.add(childList2);
        
        return childList;
        
    }
}
