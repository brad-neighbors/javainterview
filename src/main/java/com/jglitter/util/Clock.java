package com.jglitter.util;

import java.util.Date;

/**
 * Specifies operations for working with time in a deterministic way.
 */
public interface Clock {

    /**
     * @return The current date.
     */
    Date now();
}
