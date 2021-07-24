package com.spreetail.cliapp.cliapp;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.List;
import java.util.Set;

@ShellComponent
public class Commands {

    final Store store;

    public Commands(Store store) {
        this.store = store;
    }


    @ShellMethod(value = "Add member for provided key.", key = "ADD")
    public String add(String key, String value) {
        boolean added = store.addMemberForKey(key, value);
        return added ? "Added" : "ERROR, member already exists for key";
    }

    @ShellMethod(value = "Get list of keys.", key = "KEYS")
    public Set<String> keys() {
        return store.getKeys() ;
    }

    @ShellMethod(value = "Get all members for given key.", key = "MEMBERS")
    public String members(String key) {
        Set<String> set = store.getMembers(key);
        if (set == null) {
            return "ERROR, key does not exist.";
        }
        return set.toString();
    }

    @ShellMethod(value = "Removes a member from a given key.", key = "REMOVE")
    public String remove(String key, String member) {
        boolean removed = store.removeMember(key, member);
        return removed ? "Removed" : "ERROR, member does not exist.";
    }

    @ShellMethod(value = "Removes all members and key.", key = "REMOVEALL")
    public String removeAll(String key) {
        boolean removed = store.removeAll(key);
        return removed ? "Removed" : "ERROR, key does not exist.";
    }

    @ShellMethod(value = "Removes all keys and members.", key = "CLEAR")
    public String clear() {
        store.clear();
        return "Cleared";
    }

    @ShellMethod(value = "Return true/false if key exists.", key = "KEYEXISTS")
    public boolean keyExists(String key) {
        return keys().contains(key);
    }

    @ShellMethod(value = "Return true/false if key exists.", key = "MEMBEREXISTS")
    public boolean memberExists(String key, String member) {
        if (keyExists(key)) {
            return store.getMembers(key).contains(member);
        }
        return false;
    }

    @ShellMethod(value = "List all members in the dictionary.", key = "ALLMEMBERS")
    public List<String> allMembers() {
        return store.allMembers();
    }

    @ShellMethod(value = "List all items in the dictionary.", key = "ITEMS")
    public List<String> items() {
        return store.getItems();
    }
}
