package pcg;

import java.io.InputStream;


public abstract class PCGKernel extends InputStream implements IPCGKernel
{
  @Override
  public void setStreamUnique()
  {
    setStream( hashCode() );
  }


  @Override
  public boolean seek( long offset )
  {
    return false;
  }


  @Override
  public short next16()
  {
    return (short) ((short)next8() << 8 ^ next8());
  }


  @Override
  public int next32()
  {
    return (int)next16() << 16 ^ next16();
  }


  @Override
  public long next64()
  {
    return (long)next32() << 32 ^ next32();
  }


  @Override
  public int read()
  {
    return next8();
  }
}

