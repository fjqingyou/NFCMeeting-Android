

package com.nfcmeeting.nfcmeeting.http.error;

/**
 * Created_Time by ThirtyDegreesRay on 2017/10/12 14:35:50
 */

public class UnauthorizedError extends HttpError {

    public UnauthorizedError() {
        super(HttpErrorCode.UNAUTHORIZED);
    }

}
