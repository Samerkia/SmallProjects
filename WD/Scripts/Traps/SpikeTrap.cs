using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class SpikeTrap : MonoBehaviour
{
    [SerializeField]
    private GameObject traps;

    void OnTriggerEnter(Collider col)
    {
       if (col.gameObject.CompareTag("Player"))
       {
            StartCoroutine(ActivateSpikes());
            
       }
    }

    public IEnumerator ActivateSpikes()
    {
        int totalFrames = 2000;
        float frame = 0;
        Debug.Log(traps.transform.position.y);
        Vector3 SpikeUp = new Vector3(traps.transform.position.x, -2.40f, traps.transform.position.z);
        Vector3 Spiked = new Vector3(traps.transform.position.x, -2.40f, traps.transform.position.z);
        while (frame <= totalFrames)
        {
            traps.transform.position = Vector3.Lerp(traps.transform.position, SpikeUp, frame / (float)totalFrames);
            frame++;
        }
        yield return new WaitForSeconds(2);
        traps.SetActive(false);
    }
}
