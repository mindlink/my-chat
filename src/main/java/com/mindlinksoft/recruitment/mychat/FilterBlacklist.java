/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mindlinksoft.recruitment.mychat;

/**
 *
 * @author Michael
 */
public class FilterBlacklist extends Filter {

    @Override
    void filterAction(Message message, Object words, Object replacement) {
        for (String w : (String[]) words) {
            message.content = message.content.replaceAll(w, (String) replacement);
        }

    }

}
