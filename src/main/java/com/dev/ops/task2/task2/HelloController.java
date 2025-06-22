package com.dev.ops.task2.task2;

import ch.qos.logback.core.util.StringUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.nio.file.Files;
import java.nio.file.Paths;

@RestController
public class HelloController {

	@Value("${PARENT_HOSTNAME}")
	private String hostname;

	@GetMapping("/hi")
	public String hello() {

		String gitCommitHash = null;
		try {
			try {
				gitCommitHash = Files.readString(Paths.get("/app/GIT_COMMIT")).trim();
			} catch (IOException e) {
				e.printStackTrace();
			}

			if (StringUtil.isNullOrEmpty(hostname)) {
				InetAddress localHost = InetAddress.getLocalHost();
				hostname = localHost.getHostName();
			}

			if (StringUtil.isNullOrEmpty(gitCommitHash)) {
				gitCommitHash = getLastGitCommitHash();
			}

		} catch (Exception e) {
			System.err.println("Could not determine local host: " + e.getMessage());
		}

		/**
		 * there is a issue, if i created a image locally and run that image then
		 * system does not show the git commit file becasue we did not copy the
		 * git commit file for local development.
		 * */

		return "Node: " + hostname + " and Git commit hash: " + gitCommitHash;
	}

	public static String getLastGitCommitHash() {
		try {
			// Execute the Git command to get the last commit hash
			Process process = Runtime.getRuntime().exec("git rev-parse HEAD");

			// Read the output of the command
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line;
			StringBuilder output = new StringBuilder();
			while ((line = reader.readLine()) != null) {
				output.append(line);
			}

			// Wait for the process to complete and check for errors
			int exitCode = process.waitFor();
			if (exitCode == 0) {
				return output.toString().trim(); // Return the commit hash
			} else {
				// Read error stream if command failed
				BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
				StringBuilder errorOutput = new StringBuilder();
				while ((line = errorReader.readLine()) != null) {
					errorOutput.append(line);
				}
				System.err.println("Git command failed with exit code " + exitCode + ": " + errorOutput.toString());
				return null;
			}

		} catch (IOException | InterruptedException e) {
			System.err.println("Error executing Git command: " + e.getMessage());
			return null;
		}
	}

}
