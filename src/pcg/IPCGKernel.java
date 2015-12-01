package pcg;

import rngtools.ISeekableRNG;


public interface IPCGKernel extends ISeekableRNG
{
  public void setState( long state );

  public void setStream();
  public void setStreamUnique();
  public void setStream( long inc );
}

