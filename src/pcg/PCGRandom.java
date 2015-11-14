package pcg;

import java.util.Random;

/*
 * PCG Random Number Generation for Java.
 *  Replacement for java.util.Random
 *
 * Copyright 2015 Charles Griffiths <cs.griffiths@yahoo.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * For additional information about the PCG random number generation scheme,
 * including its license and other licensing options, visit
 *
 *       http://www.pcg-random.org
 */


public class PCGRandom extends Random
{
private static final long serialVersionUID = 2L;
protected IPCGKernel kernel;


  public PCGRandom() {}


  public PCGRandom( long seed )
  {
    super( seed );
  }


  public PCGRandom( IPCGKernel kernel )
  {
    this.kernel = kernel;
  }


  @Override
  protected int next( int bits )
  {
    return (kernel.next32() >> (32-bits)) & (int)((1L<<bits) - 1);
  }


  @Override
  public void setSeed( long seed )
  {
    super.setSeed( seed );

    if (null == kernel)
      kernel = new PCG32();

    kernel.setState( seed );
  }
}

