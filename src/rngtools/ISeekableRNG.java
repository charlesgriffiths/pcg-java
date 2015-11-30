package rngtools;


public interface ISeekableRNG
{
  public void setState( byte b[] );
  public boolean seek( long position );
  public void advance( long amount );
  public int blockSize();

  public long skipBytes( long amount );

  public byte next8();
  public short next16();
  public int next32();
  public long next64();
  public void next( byte b[] );
  public void next( byte b[], int offset, int length );

  public ISeekableRNG deepCopy();
  public void setState( long seed );
}

