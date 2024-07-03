package com.coderscampus.test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.jupiter.api.Test;

import com.coderscampus.assignment.Assignment8;

public class Assignment8Test {
	@Test
	public void getData() {

		Assignment8 assignment = new Assignment8();
		List<CompletableFuture<List<Integer>>> tasks = new ArrayList<>();
		List<Integer> integerList = new ArrayList<Integer>();
		HashSet<Integer> integerSet = new HashSet<Integer>();
		ExecutorService pool = Executors.newCachedThreadPool();
		for (int i = 0; i < 1000; i++) {

			CompletableFuture<List<Integer>> numbersList = CompletableFuture.supplyAsync(() -> assignment.getNumbers(),
					pool);
			tasks.add(numbersList);

		}
		while (tasks.stream().filter(CompletableFuture::isDone).count() < 1000) {
			System.out.println(
					"Number of completed threads: " + tasks.stream().filter(CompletableFuture::isDone).count());
		}

		System.out.println("Number of completed threads: " + tasks.stream().filter(CompletableFuture::isDone).count());

		tasks.stream().forEach(task -> {
			try {
				task.get().forEach(integer -> integerSet.add(integer));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		
		tasks.stream().forEach(task -> {
			try {
				task.get().forEach(integer -> integerList.add(integer));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		
		System.out.println("Number of unique integers in output.txt: " + integerSet.size());
		
		for(int number : integerSet) {
			int intRepeatCount = 0;
			for(int i = 0; i < integerList.size(); i++) {
				if (number == integerList.get(i)) {
					intRepeatCount++;
				}
			}
			System.out.println(number + " = " + intRepeatCount);
		}
		
		
	}
}
