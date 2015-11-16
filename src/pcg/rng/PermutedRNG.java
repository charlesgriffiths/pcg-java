package pcg.rng;


abstract public class PermutedRNG extends SeekableRNG
{
protected ISeekableRNG source;
protected int inputByteAmount, outputByteAmount;


  @SuppressWarnings("unused")
  private PermutedRNG() {}


  public PermutedRNG( SeekableRNG rng, int in, int out )
  {
    source = rng;
    inputByteAmount = in;
    outputByteAmount = out;
  }


  @Override
  public void setState( byte[] b )
  {
    source.setState( b );
  }


  @Override
  public void seek( long position )
  {
    source.seek( position / outputByteAmount * inputByteAmount );
  }


  @Override
  public void advance( long amount )
  {
    source.advance( amount / outputByteAmount * inputByteAmount );
  }


  @Override
  protected void deepCopy( ISeekableRNG target )
  {
    if (target instanceof PermutedRNG)
    {
    PermutedRNG pr = (PermutedRNG) target;

      pr.source = source.deepCopy();
      pr.inputByteAmount = inputByteAmount;
      pr.outputByteAmount = outputByteAmount;
    }
  }

}

