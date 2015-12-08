package rngtools;


public interface IRNG
{
  // starts at initialization
  public boolean seek( long position );
  // starts at current state
  public void advance( long amount );

  public boolean isSeekable();
  // how many random bytes generated per unit advance
  public int blockSize();

  // these two functions are expected to discard leftover bits and advance state
  public int next( int bits );
  public long nextl( int bits );

  // return blocksize bytes
  public byte[] next();
  // return b.length bytes
  public void next( byte b[] );
  // return length bytes
  public void next( byte b[], int offset, int length );

  public IRNG deepCopy();
  public void setState( byte b[] );
}

