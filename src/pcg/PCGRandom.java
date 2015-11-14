package pcg;

import java.util.Random;


public class PCGRandom extends Random
{
private static final long serialVersionUID = 1L;
long state = 0, inc = 1;

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

  protected long next64()
  {
    return ((long)next32())<<32 ^ next32();
  }

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
  public long nextLong()
  {
    return next64();
  }


  @Override
  public void setSeed( long seed )
  {
    state = seed;
  }
}

