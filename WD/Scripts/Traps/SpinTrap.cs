using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class SpinTrap : MonoBehaviour
{
    [SerializeField]
    private GameObject traps;
    protected bool notSpinning = true;

    void OnTriggerEnter(Collider col)
    {
       if (col.gameObject.CompareTag("Player") && notSpinning)
       {
            StartCoroutine(RotateForSeconds());            
       }
    }
    
    int getSpeed()
    {
        return 250;
    }

    IEnumerator RotateForSeconds() //Call this method with StartCoroutine(RotateForSeconds());
    {
        float time = 5;     //How long will the object be rotated?
        notSpinning = false;
 
        while(time > 0)     //While the time is more than zero...
        {
            traps.transform.Rotate(Vector3.left, Time.deltaTime * getSpeed() );     //...rotate the object.
            time -= Time.deltaTime;     //Decrease the time- value one unit per second.
    
            yield return null;     //Loop the method.
        }
        notSpinning = true;
    }
}
