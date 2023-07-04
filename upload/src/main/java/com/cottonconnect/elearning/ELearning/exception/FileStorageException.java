package com.cottonconnect.elearning.ELearning.exception;

public class FileStorageException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7697955747931931280L;

	public FileStorageException(String message) {
		super(message);
	}

	public FileStorageException(String message, Throwable cause) {
		super(message, cause);
	}
}
