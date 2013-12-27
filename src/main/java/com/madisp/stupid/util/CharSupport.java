package com.madisp.stupid.util;
/*
 * [The "BSD license"]
 *  Copyright (c) 2012 Terence Parr
 *  Copyright (c) 2012 Sam Harwell
 *  All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions
 *  are met:
 *
 *  1. Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *  2. Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *  3. The name of the author may not be used to endorse or promote products
 *     derived from this software without specific prior written permission.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 *  IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 *  OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 *  IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 *  INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 *  NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 *  DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 *  THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 *  (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 *  THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

// Note, this class is from the antlr4 source code. -madis

public class CharSupport {
	/** When converting ANTLR char and string literals, here is the
	 *  value set of escape chars.
	 */
	/* package */ static int ANTLRLiteralEscapedCharValue[] = new int[255];

	/** Given a char, we need to be able to show as an ANTLR literal.
	 */
	/* package */ static String ANTLRLiteralCharValueEscape[] = new String[255];

	static {
		ANTLRLiteralEscapedCharValue['n'] = '\n';
		ANTLRLiteralEscapedCharValue['r'] = '\r';
		ANTLRLiteralEscapedCharValue['t'] = '\t';
		ANTLRLiteralEscapedCharValue['b'] = '\b';
		ANTLRLiteralEscapedCharValue['f'] = '\f';
		ANTLRLiteralEscapedCharValue['\\'] = '\\';
		ANTLRLiteralEscapedCharValue['\''] = '\'';
		ANTLRLiteralEscapedCharValue['"'] = '"';
		ANTLRLiteralCharValueEscape['\n'] = "\\n";
		ANTLRLiteralCharValueEscape['\r'] = "\\r";
		ANTLRLiteralCharValueEscape['\t'] = "\\t";
		ANTLRLiteralCharValueEscape['\b'] = "\\b";
		ANTLRLiteralCharValueEscape['\f'] = "\\f";
		ANTLRLiteralCharValueEscape['\\'] = "\\\\";
		ANTLRLiteralCharValueEscape['\''] = "\\'";
	}

	/** Given char x or \t or \u1234 return the char value;
	 *  Unnecessary escapes like '\{' yield -1.
	 */
	public static int getCharValueFromCharInGrammarLiteral(String cstr) {
		switch ( cstr.length() ) {
			case 1 :
				// 'x'
				return cstr.charAt(0); // no escape char
			case 2 :
				if ( cstr.charAt(0)!='\\' ) return -1;
				// '\x'  (antlr lexer will catch invalid char)
				if ( Character.isDigit(cstr.charAt(1)) ) return -1;
				int escChar = cstr.charAt(1);
				int charVal = ANTLRLiteralEscapedCharValue[escChar];
				if ( charVal==0 ) return -1;
				return charVal;
			case 6 :
				// '\u1234'
				if ( !cstr.startsWith("\\u") ) return -1;
				String unicodeChars = cstr.substring(2, cstr.length());
				return Integer.parseInt(unicodeChars, 16);
			default :
				return -1;
		}
	}

	public static String getStringFromGrammarStringLiteral(String literal) {
		StringBuilder buf = new StringBuilder();
		int i = 1; // skip first quote
		int n = literal.length()-1; // skip last quote
		while ( i < n ) { // scan all but last quote
			int end = i+1;
			if ( literal.charAt(i) == '\\' ) {
				end = i+2;
				if ( (i+1)>=n ) break; // ignore spurious \ on end
				if ( literal.charAt(i+1) == 'u' ) end = i+6;
			}
			if ( end>n ) break;
			String esc = literal.substring(i, end);
			int c = getCharValueFromCharInGrammarLiteral(esc);
			if ( c==-1 ) { buf.append(esc); }
			else buf.append((char)c);
			i = end;
		}
		return buf.toString();
	}
}