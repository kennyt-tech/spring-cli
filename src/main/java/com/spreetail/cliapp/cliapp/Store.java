package com.spreetail.cliapp.cliapp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class Store {
    private static Logger logger = LoggerFactory.getLogger(Store.class);
    private HashMap<String, Set<String>> map;

    Store() {
        map = new HashMap<>();
    }

    public static void printKeys() {
        logger.info("print keys");
    }

    /**
     * Return a Set of keys from the map.
     * @return  Set of keys
     */
    public Set<String> getKeys() {
        return map.keySet();
    }

    /**
     * Get the list of members for a specific key.
     * @param key   - String
     * @return      - Set of Strings
     */
    public Set<String> getMembers(String key) {
        return map.get(key);
    }

    /**
     * Adds member to map if the member is not present for the given key and returns `true`.  If the member already
     * exists for the given key, then `false` is returned.
     * @param key       - String key
     * @param member    - String member
     * @return boolean  - boolean: true if member was added.  false if the member is already present in the key.
     */
    public boolean addMemberForKey(String key, String member) {
        if (map.containsKey(key)) {
            return map.get(key).add(member);
        } else {
            map.put(key, getNewHashSet(member));
            return true;
        }
    }

    /**
     * Return a new list with the one provided member.
     * @param member    - String
     * @return          - List of String members
     */
    private Set<String> getNewHashSet(String member){
        Set<String> list = new HashSet<>();
        list.add(member);
        return list;
    }

    /**
     * Remove a member for the given key.  If key or member is not present, return false.  If key as no members left,
     * remove the key.
     * @param key       - String
     * @param member    - String
     * @return          - boolean
     */
    public boolean removeMember(String key, String member) {
        Set<String> targetSet = map.get(key);
        if (targetSet == null || !targetSet.contains(member)) {
            return false;
        } else {
            targetSet.remove(member);
            if (targetSet.size() == 0) {
                map.remove(key);
            }
        }
        return true;
    }

    /**
     * Removes a key and value from the map.
     * @param key   - String key
     * @return      - boolean: true if removed, false if key doesn't exist
     */
    public boolean removeAll(String key) {
        if (map.containsKey(key)) {
            map.remove(key);
            return true;
        }
        return false;
    }

    /**
     * Clear the entire map.
     */
    public void clear() {
        map.clear();
    }

    /**
     * For each key, add all members and return total list
     * @return      - List of all members
     */
    public List<String> allMembers() {
        List<String> allList = new ArrayList<>();
        for (Map.Entry<String, Set<String>> entry : map.entrySet()) {
            allList.addAll(entry.getValue());
        }
        return allList;
    }

    /**
     * Get list of strings (Key:value) for all keys/values in the dictionary.
     * @return      - List of Strings
     */
    public List<String> getItems() {
        List<String> allList = new ArrayList<>();
        for (Map.Entry<String, Set<String>> entry : map.entrySet()) {
            for (String member : entry.getValue()) {
                allList.add(entry.getKey() + ": " + member);
            }
        }
        return allList;
    }
}
