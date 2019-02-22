package cn.tedu.store.service.ex;

public class UpdateDataException extends ServiceException {

	private static final long serialVersionUID = 4801399692605914659L;

	public UpdateDataException() {
		super();
	}

	public UpdateDataException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public UpdateDataException(String message, Throwable cause) {
		super(message, cause);
	}

	public UpdateDataException(String message) {
		super(message);
	}

	public UpdateDataException(Throwable cause) {
		super(cause);
	}

}
