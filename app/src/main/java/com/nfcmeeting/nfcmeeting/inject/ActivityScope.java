

package com.nfcmeeting.nfcmeeting.inject;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * activity生命周期内
 */
@Documented
@Scope
@Retention(RUNTIME)
public @interface ActivityScope {
}
