package pcg;


public interface IPCGKernel
{
  public void setState( long state );

  public void setStream();
  public void setStreamUnique();
  public void setStream( long inc );

  public boolean seek( long offset );

  public byte next8();
  public short next16();
  public int next32();
  public long next64();
}

