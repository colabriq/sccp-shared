package com.goodforgoodbusiness.shared;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

public class FileLoader {
	private static void scanInto(List<File> output, final File path) {
		if (!path.getName().startsWith(".")) {
			if (path.isDirectory()) {
				for (final File entry : path.listFiles()) {
					scanInto(output, entry);
				}
			}
			else {
				output.add(path);
			}
		}
	}
	
	public static void scan(File folder, Consumer<File> consumer) {
		var files = new LinkedList<File>();
		scanInto(files, folder);
		for (File file : files) {
			consumer.accept(file);
		}
	}
}
