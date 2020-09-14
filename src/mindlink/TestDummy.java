/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mindlink;

/**
 *
 * @author andyt
 */
public class TestDummy {
    public static void main(String[] args){
    String s = "blacklist:1,2,3,4,5,6";
    String[] aftersplit = new String[] {};
    
    aftersplit = s.split(":");
    aftersplit = aftersplit[1].split(",");
    
        System.out.println(aftersplit[0]);
    
    }
}
