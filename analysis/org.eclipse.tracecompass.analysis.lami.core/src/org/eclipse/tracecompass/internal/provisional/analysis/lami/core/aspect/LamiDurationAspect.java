/*******************************************************************************
 * Copyright (c) 2016 EfficiOS Inc., Michael Jeanson
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package org.eclipse.tracecompass.internal.provisional.analysis.lami.core.aspect;

import java.util.Comparator;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.tracecompass.internal.provisional.analysis.lami.core.module.LamiTableEntry;
import org.eclipse.tracecompass.internal.provisional.analysis.lami.core.types.LamiData;
import org.eclipse.tracecompass.internal.provisional.analysis.lami.core.types.LamiDuration;

/**
 * Aspect for a time range duration
 *
 * @author Michael Jeanson
 */
public class LamiDurationAspect extends LamiTableEntryAspect {

    private final int fColIndex;

    /**
     * Constructor
     *
     * @param colName
     *            Column name
     * @param colIndex
     *            Column index
     */
    public LamiDurationAspect(String colName, int colIndex) {
        super(colName, "ns"); //$NON-NLS-1$
        fColIndex = colIndex;
    }

    @Override
    public boolean isContinuous() {
        return true;
    }

    @Override
    public boolean isTimeStamp() {
        return false;
    }

    @Override
    public boolean isTimeDuration() {
        return true;
    }

    @Override
    public @Nullable String resolveString(LamiTableEntry entry) {
        Number n = resolveNumber(entry);
        if (n == null) {
            return String.valueOf(entry.getValue(fColIndex));
        }
        return n.toString();
    }

    @Override
    public @Nullable Number resolveNumber(@NonNull LamiTableEntry entry) {
        LamiData data = entry.getValue(fColIndex);
        if (data instanceof LamiDuration) {
            LamiDuration duration = (LamiDuration) data;

            // TODO: Consider low and high limits here.
            return duration.getValue();
        }
        return null;
    }

    @Override
    public @NonNull Comparator<@NonNull LamiTableEntry> getComparator() {
        return LamiComparators.getLongComparator(this::resolveNumber);
    }

}
