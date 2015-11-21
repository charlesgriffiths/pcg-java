package pcg;

import java.util.Random;

import pcg.rng.ISeekableRNG;


// Seekable Kernel Random, drop in replacement for java.util.Random
public class SKRandom extends Random
{
private static final long serialVersionUID = 1L;

ISeekableRNG source;


  public SKRandom()
  {
    if (null == source) source = new PCG32();
  }


  public SKRandom( long seed )
  {
    super( seed );
    if (null == source) source = new PCG32( seed );
  }


  public SKRandom( ISeekableRNG rng )
  {
    if (null == source) source = rng;
  }


  @Override
  protected int next( int bits )
  {
    return (source.next32() >> (32-bits)) & (int)((1L<<bits) - 1);
  }


  @Override
  public void setSeed( long seed )
  {
    super.setSeed( seed );

    if (null == source)
      source = new PCG32();

    source.setState( seed );
  }
}

