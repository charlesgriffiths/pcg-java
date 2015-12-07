package rngtools;


public interface IBitStream
{
  public boolean next();
  public int next( int bits );
  public long nextl( int bits );
}

