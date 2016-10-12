/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.ignite.internal.pagemem.wal.record.delta;

import java.nio.ByteBuffer;
import org.apache.ignite.IgniteCheckedException;
import org.apache.ignite.internal.pagemem.PageMemory;
import org.apache.ignite.internal.pagemem.wal.record.WALRecord;
import org.apache.ignite.internal.util.tostring.GridToStringExclude;
import org.apache.ignite.internal.util.typedef.internal.S;
import org.apache.ignite.internal.util.typedef.internal.U;

/**
 * Abstract page delta record.
 */
public abstract class PageDeltaRecord extends WALRecord {
    /** */
    private int cacheId;

    /** */
    @GridToStringExclude
    private long pageId;

    /**
     * @param cacheId Cache ID.
     * @param pageId Page ID.
     */
    protected PageDeltaRecord(int cacheId, long pageId) {
        this.cacheId = cacheId;
        this.pageId = pageId;
    }

    /**
     * @return Page ID.
     */
    public long pageId() {
        return pageId;
    }

    /**
     * @return Cache ID.
     */
    public int cacheId() {
        return cacheId;
    }

    /**
     * @return Init new page record flag.
     */
    public boolean initNew() {
        return false;
    }

    /**
     * Apply changes from this delta to the given page.
     * It is assumed that the given buffer represents page state right before this update.
     *
     *
     * @param pageMem Page memory to apply this record.
     * @param buf Page buffer.
     * @throws IgniteCheckedException If failed.
     */
    public abstract void applyDelta(PageMemory pageMem, ByteBuffer buf) throws IgniteCheckedException;

    /** {@inheritDoc} */
    @Override public String toString() {
        return S.toString(PageDeltaRecord.class, this,
            "pageId", U.hexLong(pageId),
            "super", super.toString());
    }
}
