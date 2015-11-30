package rngtools;


public interface IRNGBitStream extends ISeekableRNG
{
  public boolean next();
  public int next( int bits );
  public int nextl( int bits );
}

