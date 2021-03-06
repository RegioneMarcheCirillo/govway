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
/*
 * Modificato per supportare le seguenti funzionalita':
 * - Generazione ID all'interno delle interfacce di OpenSPCoop2
 * 
 * GovWay - A customizable API Gateway 
 * https://govway.org
 * 
 * Copyright (c) 2005-2020 Link.it srl (https://link.it). 
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 3, as published by
 * the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package org.openspcoop2.utils.id.apache.serial;

import org.openspcoop2.utils.id.apache.AbstractStringIdentifierGenerator;

/**
 * <code>PrefixedLeftPaddedNumericGenerator</code> is an Identifier Generator
 * that generates a left-padded incrementing number with a prefix as a String object.
 *
 * <p>All generated ids have the same length (prefixed and padded with 0's
 * on the left), which is determined by the <code>size</code> parameter passed
 * to the constructor.<p>
 *
 * <p>The <code>wrap</code> property determines whether or not the sequence wraps
 * when it reaches the largest value that can be represented in <code>size</code>
 * base 10 digits. If <code>wrap</code> is false and the the maximum representable
 * value is exceeded, an {@link IllegalStateException} is thrown.</p>
 *
 * @author Commons-Id team
 * @version $Id$
 */
/**
 * OpenSPCoop2
 *
 * @author $Author$
 * @version $Rev$, $Date$
 */
public class PrefixedLeftPaddedNumericGenerator extends AbstractStringIdentifierGenerator {

    /** Prefix. */
    private final String prefix;

    /** Should the counter wrap. */
    private boolean wrap = true;

    /** The counter. */
    private char[] count = null;

    /** '9' char. */
    private static final char NINE_CHAR = '9';


    /**
     * Create a new prefixed left-padded numeric generator with the specified prefix.
     *
     * @param prefix prefix, must not be null or empty
     * @param wrap should the factory wrap when it reaches the maximum
     *  value that can be represented in <code>size</code> base 10 digits
     *  (or throw an exception)
     * @param size the size of the identifier, including prefix length
     * @throws IllegalArgumentException if size less prefix length is not at least one
     * @throws NullPointerException if prefix is <code>null</code>
     */
    public PrefixedLeftPaddedNumericGenerator(String prefix, boolean wrap, int size) {
        super();

        if (prefix == null) {
            throw new NullPointerException("prefix must not be null");
        }
        if (size < 1) {
            throw new IllegalArgumentException("size must be at least one");
        }
        if (size <= prefix.length()) {
            throw new IllegalArgumentException("size less prefix length must be at least one");
        }
        this.wrap = wrap;
        this.prefix = prefix;

        int countLength = size - prefix.length();
        this.count = new char[countLength];
        for (int i = 0; i < countLength; i++) {
            this.count[i] = '0';
        }
    }


    /**
     * Return the prefix for this prefixed numeric generator.
     *
     * @return the prefix for this prefixed numeric generator
     */
    public String getPrefix() {
        return this.prefix;
    }

    @Override
	public long maxLength() {
        return this.count.length + this.prefix.length();
    }

    @Override
	public long minLength() {
        return this.count.length + this.prefix.length();
    }

    /**
     * Returns the (constant) size of the strings generated by this generator.
     *
     * @return the size of generated identifiers
     */
    public int getSize() {
        return this.count.length + this.prefix.length();
    }

    /**
     * Getter for property wrap.
     *
     * @return <code>true</code> if this generator is set up to wrap
     */
    public boolean isWrap() {
        return this.wrap;
    }

    /**
     * Setter for property wrap.
     *
     * @param wrap should the factory wrap when it reaches the maximum
     *  value that can be represented in <code>size</code> base 10 digits
     *  (or throw an exception)
     */
    public void setWrap(boolean wrap) {
        this.wrap = wrap;
    }

    @Override
	public String nextStringIdentifier() throws MaxReachedException {
        for (int i = this.count.length - 1; i >= 0; i--) {
            switch (this.count[i]) {
                case NINE_CHAR:  // 9
                	this.count[i] = '0';
                    if (i == 0 && !this.wrap) {
                        throw new MaxReachedException
                        ("The maximum number of identifiers has been reached");
                    }
                    break;

                default:
                	this.count[i]++;
                    i = -1;
                    break;
            }
        }

        StringBuilder sb = new StringBuilder(this.prefix);
        sb.append(this.count);
        return sb.toString();
    }
}
