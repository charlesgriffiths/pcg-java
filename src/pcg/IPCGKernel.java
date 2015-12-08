package pcg;

import rngtools.IRNG;


public interface IPCGKernel extends IRNG
{
  public void setStream();
  public void setStreamUnique();
  public void setStream( long inc );
}

