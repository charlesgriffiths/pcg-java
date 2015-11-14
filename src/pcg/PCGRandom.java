package pcg;

import java.util.Random;

/*
 * PCG Random Number Generation for Java.
 *
 * Copyright 2015 Charles Griffiths <cs.griffiths@yahoo.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * For additional information about the PCG random number generation scheme,
 * including its license and other licensing options, visit
 *
 *       http://www.pcg-random.org
 */

/*
 * This code is derived from the basic C implementation by
 * Melissa O'Neill <oneill@pcg-random.org>, which is derived
 * from the full C implementation, which is in turn derived from the
 * canonical C++ PCG implementation. The C++ version has many additional
 * features and is preferable if you can use C++ in your project.
 */

public class PCGRandom extends Random
{
private static final long serialVersionUID = 1L;
long state = 0, inc = 1;


  public PCGRandom() {}

  public PCGRandom( long seed )
  {
    super( seed );
  }

/*
// *Really* minimal PCG32 code / (c) 2014 M.E. O'Neill / pcg-random.org
// Licensed under Apache License 2.0 (NO WARRANTY, etc. see website)

typedef struct { uint64_t state;  uint64_t inc; } pcg32_random_t;

uint32_t pcg32_random_r(pcg32_random_t* rng)
{
    uint64_t oldstate = rng->state;
    // Advance internal state
    rng->state = oldstate * 6364136223846793005ULL + (rng->inc|1);
    // Calculate output function (XSH RR), uses old state for max ILP
    uint32_t xorshifted = ((oldstate >> 18u) ^ oldstate) >> 27u;
    uint32_t rot = oldstate >> 59u;
    return (xorshifted >> rot) | (xorshifted << ((-rot) & 31));
}
*/


  protected int next32()
  {
    state = state * 6364136223846793005L + inc|1;

    return Integer.rotateRight( (int) (((state >>> 18) ^ state) >>> 27), (int) (state >>> 59) );
  }


  @Override
  protected int next( int bits )
  {
    return (next32() >> (32-bits)) & (int)((1L<<bits) - 1);
  }


  @Override
  public void setSeed( long seed )
  {
    state = seed;
// System.out.println( "PCGRandom seed set to " + seed );
  }
}

