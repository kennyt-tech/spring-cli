package com.spreetail.cliapp.cliapp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CliAppApplicationTests {
	static Commands commands;

	@BeforeEach
	public void beforeEach() {
		Store store = new Store();
		commands = new Commands(store);

		commands.add("key1", "value1");
		commands.add("key1", "value2");
		commands.add("key2", "value2");
	}


	@Test
	void test_all_keys_present() {
		// Given
		Set<String> expectedKeys = new HashSet<>();
		expectedKeys.add("key1");
		expectedKeys.add("key2");

		// When
		Set<String> keys = commands.keys();

		// Then
		assertEquals(keys.size(), 2);
		expectedKeys.forEach(s -> assertTrue(keys.contains(s)) );

	}

	@Test
	void test_all_members_present() {
		// Given
		// When
		String members = commands.members("key1");

		// Then
		assertEquals(members, "[value2, value1]");
	}

	@Test
	void test_remove() {
		// Given
		assertEquals(2, commands.keys().size());
		// When
		String result = commands.remove("key1", "value2");
		String result2 = commands.remove("key1", "value1");
		String result_noKey = commands.remove("key1", "value1");

		// Then
		assertEquals("Removed", result);
		assertEquals("Removed", result2);
		// Key was removed since no more members are present
		assertEquals(1, commands.keys().size());
		assertEquals("ERROR, member does not exist.", result_noKey);
	}

	@Test
	void test_remove_error() {
		// Given
		// When
		String result = commands.remove("key1", "value2");
		String result2 = commands.remove("key1", "value2");

		// Then
		assertEquals("Removed", result);
		assertEquals("ERROR, member does not exist.", result2);
	}

	@Test
	void test_remove_all() {
		// Given
		Set<String> expectedSet = new HashSet<>();
		expectedSet.add("key2");
		// When
		String result = commands.removeAll("key1");
		String result2 = commands.removeAll("key1");

		// Then
		assertEquals("Removed", result);
		assertEquals(expectedSet, commands.keys());
		assertEquals("ERROR, key does not exist.", result2);
	}

	@Test
	void test_clear() {
		// Given
		// When
		String result = commands.clear();

		// Then
		assertEquals("Cleared", result);
		assertEquals(0, commands.keys().size());
	}

	@Test
	void test_key_exists() {
		// Given
		// When
		boolean result = commands.keyExists("key1");
		boolean result_no_key = commands.keyExists("no_key");

		// Then
		assertTrue(result);
		assertFalse(result_no_key);
	}

	@Test
	void test_member_exists() {
		// Given
		// When
		boolean result = commands.memberExists("key1", "value1");
		boolean result_no_value = commands.memberExists("key1", "no_value");
		boolean result_key_not = commands.memberExists("key_not", "value1");

		// Then
		assertTrue(result);
		assertFalse(result_no_value);
		assertFalse(result_key_not);
	}

	@Test
	void test_all_members_empty() {
		// Given
		commands.clear();
		// When
		List<String> result = commands.allMembers();

		// Then
		assertTrue(result.isEmpty());
	}

	@Test
	void test_all_members() {
		// Given
		// When
		List<String> result = commands.allMembers();

		// Then
		assertEquals(3, result.size());
	}

	@Test
	void test_items() {
		// Given
		List<String> expected = new ArrayList<>();
		expected.add("key1: value2");
		expected.add("key1: value1");
		expected.add("key2: value2");
		// When
		List<String> result = commands.items();

		// Then
		assertEquals(3, result.size());
		assertEquals(expected, result);
	}
}
