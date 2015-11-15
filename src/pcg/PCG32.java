package pcg;

import java.io.Serializable;


/*
 * This code is derived from the canonical C++ PCG implementation by
 * Melissa O'Neill <oneill@pcg-random.org>. The C++ version has many
 * additional features and is preferable if you can use C++ in your project.
 */


public final class PCG32 extends PCGKernel implements Serializable
{
private static final long serialVersionUID = 1L;

public static final long mul64 = 6364136223846793005L, inc64 = 1442695040888963407L;

private long state = 1, inc = inc64;
private long markstate = 1;


  public PCG32()
  {
  }


  public PCG32( long seed )
  {
    setState( seed );
  }


  @Override
  public void setState( long seed )
  {
    state = seed;
  }


  @Override
  public void setStream()
  {
    setStream( inc64 );
  }


  @Override
  public void setStream( long inc )
  {
    this.inc = inc;
  }


  @Override
  public byte next8()
  {
    return (byte) (next32() >> 24);
  }


  @Override
  public short next16()
  {
    return (short) (next32() >> 16);
  }


/* This output function in java is meant to serve the same purpose as the following:
 * 
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

 Similar functionality may be obtained by setting inc to 1 with setStream( 1L );
*/

  @Override
  public int next32()
  {
    state = state * mul64 + inc;

    return Integer.rotateRight( (int) (((state >>> 18) ^ state) >>> 27), (int) (state >>> 59) );
  }


  @Override
  public boolean markSupported()
  {
    return true;
  }


  @Override
  public void mark( int readlimit )
  {
    markstate = state;
  }


  @Override
  public void reset()
  {
    setState( markstate );
  }


  @Override
  public long skip( long n )
  {
    setState( state + stateoffset( n, mul64, inc ));

    return n;
  }


  @Override
  public boolean seek( long offset )
  {
    setState( 1 + stateoffset( offset, mul64, inc ));

    return true;
  }
}

