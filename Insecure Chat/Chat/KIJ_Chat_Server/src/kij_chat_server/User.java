/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kij_chat_server;

import java.util.ArrayList;

/**
 *
 * @author santen-suru
 */
public class User {
    // User-Password list
      private ArrayList<Pair<String,Pair<String,String>>> _userlist = new ArrayList<>();
    
    User() {
        _userlist.add(new Pair("Admin", new Pair("Admin", "c1c224b03cd9bc7b6a86d77f5dace40191766c485cd55dc48caf9ac873335d6f")));
        _userlist.add(new Pair("Andi", new Pair("Andi", "b6f365852088565fe1b7e6eae8ef53d7b0f944f4e1d420192f7032c975744318")));
        _userlist.add(new Pair("Budi", new Pair("Budi", "c78aa6f52b1f8cac26e4c59f0ffcddfd53884ae8e7fb2522f2176faaa3d3fedd")));
        _userlist.add(new Pair("Rudi", new Pair("Rudi", "2457164e175132dffb87ecd6f23764cf5ff2306bf9d815231f4c3bf6cfd972dc")));
        _userlist.add(new Pair("Luci", new Pair("Luci", "cbda46ab4dda33d86cb4b4ee4b72bec0f8265e1712e82c24ca212a0adee619ba")));
    }
    
    public ArrayList <Pair<String,Pair<String,String>>> getUserList() {
        return _userlist;
    }
}
