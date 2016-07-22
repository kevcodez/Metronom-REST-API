/**
 * MIT License
 * 
 * Copyright (c) 2016 Kevin Grüneberg
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the
 * Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 **/
package de.kevcodez.metronom.utility;

/**
 * Helper class to throw unchecked exceptions.
 * 
 * @author Kevin Grüneberg
 *
 */
public class Exceptions {

  private Exceptions() {
    // Hidden constructor
  }

  /**
   * Rethrows a checked exception as unchecked exception. This method tricks the compiler into thinking the exception is
   * unchecked, rather than wrapping the given exception in a new {@code RuntimeException}.
   * 
   * @param exc checked exception
   * @return syntactically, a runtime exception (but never actually returns)
   */
  public static RuntimeException unchecked(Exception exc) {
    Exceptions.<RuntimeException> adapt(exc);
    return null;
  }

  @SuppressWarnings("unchecked")
  private static <T extends Exception> void adapt(Exception exc) throws T {
    throw (T) exc;
  }
}
